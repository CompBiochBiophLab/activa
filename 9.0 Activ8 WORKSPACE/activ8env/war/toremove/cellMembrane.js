o3djs.require('o3djs.util');
o3djs.require('o3djs.math');
o3djs.require('o3djs.rendergraph');
o3djs.require('o3djs.primitives');
o3djs.require('o3djs.material');
o3djs.require('o3djs.pack');
o3djs.require('o3djs.scene');
o3djs.require('o3djs.debug');


var cell_membrane_object = null;

var cell_membrane_protein_object = null;
var cell_membrane_channel_object = null;
var cell_membrane_half_channel_object = null;
var cell_membrane_lipid_object   = null;
var cell_membrane_lipid_object_reverted = null;
var all_finished = 0;  //to check if we loaded 3d objects, since importing is asyncrenous


var lipids_structure = [];
var proteinChannels_structure = [];
var cell_proteins_structure = [];
var cell_membrane_octree_root = null;

var AABB_overlaps = false;
var RADIAN_INV = Math.PI/180.0;
var RADIAN     = 180.0/Math.PI;


var curr_Intersection_Volume = 0;

function put_AABB_Object(octree_node, curr_AABB){
    if(octree_node==null)
        return;
    var curr_node_AABB = new AABB();
    curr_node_AABB.center = octree_node.center;
    curr_node_AABB.radius = octree_node.radius;

    if(octree_node.isLeaf()){
       // cross_Sphere_Cube(radius, x, y, z, max_pnt, min_pnt)
      var overlap = intersect_AABB_AABB(curr_node_AABB, curr_AABB);
      if(overlap){
          octree_node.membrane_AABBs.push(curr_AABB);
          var intersect_vol = get_Intersection_Volume_AABB(curr_node_AABB, curr_AABB);
          octree_node.occupied_volume += intersect_vol;
          curr_AABB.octree_nodes.push(octree_node);
      }
      return;
    }

    var overlap = intersect_AABB_AABB(curr_node_AABB, curr_AABB);
    if(overlap){
        //since it is overlapping add the intersection volume to the node
        var intersect_vol = get_Intersection_Volume_AABB(curr_node_AABB, curr_AABB);
        octree_node.occupied_volume += intersect_vol;
        for(var i=0; i<8; i++){
            if(octree_node.childs[i]!=null)
               put_AABB_Object(octree_node.childs[i], curr_AABB);
        }
    }
    return;
}

//Get a Octant in the tree which has a lower resolution compare to its neighbours at a defined level
function getLowResolution_Octant(octree_node, last_level, curr_level){
    if(last_level<=curr_level)
        return null;
    if(octree_node==null)
        return null;
    else if(last_level>=curr_level+1){
        var curr_node = null;
        var canditade_regions = []; 
        for(var i=0; i<8; i++){
            if(curr_node==null && octree_node.childs[i]!=null)
                curr_node = octree_node.childs[i];
            else if(curr_node!=null && octree_node.childs[i].occupied_volume<curr_node.occupied_volume)
                curr_node = octree_node.childs[i];
        }
        for(var i=0; i<8; i++){
             if(octree_node.childs[i].occupied_volume==curr_node.occupied_volume)
                canditade_regions.push(octree_node.childs[i]);
        }
        var canditate_index = Math.random()*canditade_regions.length;
        curr_node = canditade_regions[ Math.floor(canditate_index)];
        if(last_level==curr_level+1)
            return curr_node;
        else if(curr_node!=null)  //we still did not find the right level we were planning to access
             return getLowResolution_Octant(curr_node, last_level, curr_level+1);
    }
    return null;
}


function getLowResolution_Octant_Lipids(octree_node, last_level, curr_level, y_pos){
    if(last_level<=curr_level)
        return null;
    if(octree_node==null)
        return null;
    else if(last_level>=curr_level+1){
        var curr_node = null;
        var canditade_regions = [];
        for(var i=0; i<8; i++){
            if(curr_node==null && octree_node.childs[i]!=null){
                if(Math.abs(octree_node.childs[i].center[1] - octree_node.childs[i].radius[1])>=Math.abs(y_pos - octree_node.childs[i].center[1]))
                    curr_node = octree_node.childs[i];
            }
            else if(curr_node!=null && octree_node.childs[i].occupied_volume<curr_node.occupied_volume){
                 if(Math.abs(octree_node.childs[i].center[1] - octree_node.childs[i].radius[1])>=Math.abs(y_pos - octree_node.childs[i].center[1]))
                    curr_node = octree_node.childs[i];
            }
        }
        if(curr_node==null)
            return null;
        for(var i=0; i<8; i++){
             if(octree_node.childs[i].occupied_volume==curr_node.occupied_volume){
                  if(Math.abs(octree_node.childs[i].center[1] - octree_node.childs[i].radius[1])>=Math.abs(y_pos - octree_node.childs[i].center[1]))
                    canditade_regions.push(octree_node.childs[i]);
             }
        }
        var canditate_index = Math.random()*canditade_regions.length;
        curr_node = canditade_regions[ Math.floor(canditate_index)];
        if(last_level==curr_level+1)
            return curr_node;
        else if(curr_node!=null)  //we still did not find the right level we were planning to access
             return getLowResolution_Octant_Lipids(curr_node, last_level, curr_level+1, y_pos);
    }
    return null;
}


function check_AABB_Object_Collision(octree_node, curr_AABB){
    if(octree_node==null)
        return;
    var curr_node_AABB = new AABB();
    curr_node_AABB.center = octree_node.center;
    curr_node_AABB.radius = octree_node.radius;

    if(octree_node.isLeaf()){
       // cross_Sphere_Cube(radius, x, y, z, max_pnt, min_pnt)
      for(var i=0; i<octree_node.membrane_AABBs.length; i++){
         var overlap = intersect_AABB_AABB(octree_node.membrane_AABBs[i], curr_AABB);
         if(overlap){
            curr_Intersection_Volume += get_Intersection_Volume_AABB(octree_node.membrane_AABBs[i], curr_AABB);
            AABB_overlaps = true;
            return;
         }
      }
      return;
    }
      
    
    var overlap = intersect_AABB_AABB(curr_node_AABB, curr_AABB);
    if(overlap){
        for(var i=0; i<8; i++){
            if(octree_node.childs[i]!=null)
                check_AABB_Object_Collision(octree_node.childs[i], curr_AABB);

        }
    }
}

function delete_Object(curr_node, curr_obj){
    
    if(curr_node==null)
        return;
    var overlap = intersect_AABB_AABB(curr_node, curr_obj);
    if(curr_node.isLeaf() && overlap){
        var without_obj_elements = [];
        for(var i=0; i<curr_node.membrane_AABBs.length; i++){
            if(curr_node.membrane_AABBs[i]==curr_obj)
                continue;
            without_obj_elements.push(curr_node.membrane_AABBs[i]);
        }
        curr_node.membrane_AABBs = [];
        curr_node.membrane_AABBs = without_obj_elements;
    }
    else if(!curr_node.isLeaf() && overlap){
        for(var i=0; i<8; i++){
            delete_Object(curr_node[i], curr_obj);
        }
    }   
}

//This applies Lennard Jones method to move the the objects in the membrane
//if the objects are crossing each other in the membrane it will reduce the
//intersection/collision cases

function update_Interaction_Lists(){

    var all_objects = lipids_structure.concat(proteinChannels_structure, cell_proteins_structure);
    //Update the interaction list for eacj object in the octree
    for(var i=0; i<all_objects.length; i++){
        Membrane_Elements_In_Region = [];
        all_objects[i].Interaction_Objects = [];
        getMembrane_AABBs_in_Region(cell_membrane_octree_root, all_objects[i].center, 1);
        for(var j=0; j<Membrane_Elements_In_Region.length; j++){
            if(Membrane_Elements_In_Region[j]==all_objects[i])
                continue;
            var exist = false;
            for(var t=0; t<all_objects[i].Interaction_Objects.length; t++){
                if(all_objects[i].Interaction_Objects[t]==Membrane_Elements_In_Region[j]){
                    exist = true;
                    break;
                }
            }
            if(!exist)
                all_objects[i].Interaction_Objects.push(Membrane_Elements_In_Region[j]);
        }
    }
}

function Lennard_Jones_Dynamics(constant_A, constant_B, iteration_num, time_step, border_x, border_z){

    update_Interaction_Lists();
    var all_objects = lipids_structure.concat(proteinChannels_structure, cell_proteins_structure);

    for(var m=0; m<iteration_num; m++){

        if(m%10==0){
            for(var k=0; k<all_objects.length; k++){
                delete_Referances(all_objects[k]);
            }
            for(var k=0; k<all_objects.length; k++)
                put_AABB_Object(cell_membrane_octree_root, all_objects[k]);
        }

        for(var i=0; i<all_objects.length; i++){
            var center = all_objects[i].center;
            var derivative_X = 0;
            var derivative_Z = 0;
            var velocity = all_objects[i].velocity;

            for(var j=0; j<all_objects[i].Interaction_Objects.length; j++){
                var second_center = all_objects[i].Interaction_Objects[j].center;
                var dist = Math.sqrt((center[0]-second_center[0])*(center[0]-second_center[0]) + (center[2]-second_center[2])*(center[2]-second_center[2]));
                var dist_over_a_plus_one = Math.pow(dist, 13);
                var dist_over_b_plus_one = Math.pow(dist, 7);
                var derivative_X_j = (((-1)*constant_A)/dist_over_a_plus_one + constant_B/dist_over_b_plus_one);
                derivative_X_j *= (-1)*(second_center[0] - center[0])/dist;

                var derivative_Z_j = (((-1)*constant_A)/dist_over_a_plus_one + constant_B/dist_over_b_plus_one);
                derivative_Z_j *= (-1)*(second_center[2] - center[2])/dist;
                derivative_X += derivative_X_j;
                derivative_Z += derivative_Z_j;
             }
             if(derivative_X>10)
                 derivative_X = 10;
             else if(derivative_X<-10)
                 derivative_X = -10;
             if(derivative_Z>10)
                 derivative_Z = 10;
             else if(derivative_Z<-10)
                 derivative_Z = -10;
             all_objects[i].acceleration = [(-1)*derivative_X, 0, 0.0, (-1)*derivative_Z];
             var new_acceleration = all_objects[i].acceleration;
             all_objects[i].velocity = [all_objects[i].velocity[0] + new_acceleration[0]*time_step, all_objects[i].velocity[1], all_objects[i].velocity[2]+new_acceleration[2]*time_step];
        }
        for(var i=0; i<all_objects.length;i++){
             var curr_center = [all_objects[i].center[0], all_objects[i].center[1], all_objects[i].center[2]];
             curr_center[0] = curr_center[0] + all_objects[i].velocity[0]*time_step + (1.0/2.0)*all_objects[i].acceleration[0]*(time_step*time_step);
             curr_center[1] = curr_center[1] + all_objects[i].velocity[1]*time_step + (1.0/2.0)*all_objects[i].acceleration[1]*(time_step*time_step);
             curr_center[2] = curr_center[2] + all_objects[i].velocity[2]*time_step + (1.0/2.0)*all_objects[i].acceleration[2]*(time_step*time_step);
             while(curr_center[0]>border_x[1])
                 curr_center[0] -=(border_x[1] - border_x[0]);
             while(curr_center[0]<border_x[0])
                 curr_center[0] += (border_x[1] - border_x[0]);
             while(curr_center[2]>border_z[1])
                 curr_center[2] -= (border_z[1] - border_z[0] );
             while(curr_center[2]<border_z[0])
                 curr_center[2] += (border_z[1] - border_z[0]);
        
             all_objects[i].center[0] = curr_center[0];
             all_objects[i].center[1] = curr_center[1];
             all_objects[i].center[2] = curr_center[2];
        }
    }
}


function createShadowColorMaterial(baseColor) {
  var material = g_pack.createObject('Material');
  material.drawList = g_colorViewInfo.performanceDrawList;

  material.effect = g_shadowColorEffect;
  g_shadowColorEffect.createUniformParameters(material);

  material.getParam('shadowMapSampler').value = g_shadowSampler;

  material.getParam('ambient').value = g_math.mulScalarVector(0.1, baseColor);
  material.getParam('diffuse').value = g_math.mulScalarVector(0.8, baseColor);
  material.getParam('specular').value = [1, 1, 1, 1];
  material.getParam('shininess').value = 80;

  return material;
}

function check_crossing(AABB_list, curr_bb){
    for(var i=0; i<AABB_list.length; i++){
         if(intersect_AABB_AABB(AABB_list[i], curr_bb))
             return true;
    }
    return false;
}

function createProtein_Channal(size_x, size_z, pos_y, number_proteins){
    
    for(var i=0; i<number_proteins; i++){
     var pos_x;
     var pos_z;
     var new_box = new AABB();

     var curr_node = getLowResolution_Octant(cell_membrane_octree_root, 2, 1);
     if(curr_node!=null){  //since we want to create a random point in the dimentions of this node
        pos_x = Math.random()*(curr_node.radius[0]*2);
        pos_z = Math.random()*(curr_node.radius[2]*2);
        pos_x = curr_node.center[0] + (pos_x - curr_node.radius[0]);
        pos_z = curr_node.center[2] + (pos_z - curr_node.radius[2]);
     }
     else{
         pos_x = Math.random()*size_x;
         pos_z = Math.random()*size_z;
     }
     new_box.center = [pos_x, pos_y, pos_z];
     new_box.radius = [0.40, 1.0, 0.40];

     AABB_overlaps = false;
     var crossing = false;
     check_AABB_Object_Collision(cell_membrane_octree_root, new_box);
   var tried = 0;
   for(var j=0; j<proteinChannels_structure.length && AABB_overlaps; j++){
         var curr_node = getLowResolution_Octant(cell_membrane_octree_root, 2, 1);
         var pos_x, pos_z;
         if(curr_node!=null){  //since we want to create a random point in the dimentions of this node
            pos_x = Math.random()*(curr_node.radius[0]*2);
            pos_z = Math.random()*(curr_node.radius[2]*2);
            pos_x = curr_node.center[0] + (pos_x - curr_node.radius[0]);
            pos_z = curr_node.center[2] + (pos_z - curr_node.radius[2]);
         }
         else{
             pos_x = Math.random()*size_x;
             pos_z = Math.random()*size_z;
         }
         new_box.center = [pos_x, pos_y, pos_z];
         new_box.radius = [0.4, 1.0, 0.4];

        AABB_overlaps = false;
        curr_Intersection_Volume = 0;
        var new_box_volume = new_box.getVolume();
        check_AABB_Object_Collision(cell_membrane_octree_root, new_box);

        if(AABB_overlaps){
            var overlapping_ratio =  curr_Intersection_Volume/new_box.getVolume();
            if(overlapping_ratio>0.00){
                j = 0;
                tried++;
                continue;
            }
            else
                AABB_overlaps = false;
        }
        if(tried>10)
            break;
     }
     if(tried>10)
         continue;
     new_box.type = "protein_channel";
     put_AABB_Object(cell_membrane_octree_root, new_box);
     proteinChannels_structure.push(new_box);
     var vertices  = [];
     var indexes   = [];
     for(var m=0; m<8; m++){
         var ver = new_box.getCorner(m);
         vertices.push(ver[0]);
         vertices.push(ver[1]);
         vertices.push(ver[2]);
     }
     indexes = [0,1 ,1,2, 2,3, 3,0, 4,5, 5,6, 6,7, 0,4, 1,5, 2,6, 3,7];
     var transform = g_pack.createObject('Transform');
     transform.parent = g_client.root;
     var shape = o3djs.debug.createLineShape(g_pack,
                                      o3djs.material.createConstantMaterial(g_pack,
                                                        g_colorViewInfo, [0, 1, 0, 1]),
                                                        vertices, indexes);
         //for(var i=0; i<shape_array.length; i++)
        transform.addShape(shape);
        transform.cull      = false;
        transform.visible   = true;



  }

}

function createProteins(size_x, size_y, size_z, number_proteins){

  for(var i=0; i<number_proteins; i++){
     var pos_x = Math.random()*size_x;
     //var pos_y = Math.random()*size_y;
     var pos_z = Math.random()*size_z;
     var new_box = new AABB();
     new_box.center = [pos_x, size_y/2.0, pos_z];
     new_box.radius = [0.25, 1, 0.25];
     AABB_overlaps = false;
     var crossing = false;
     check_AABB_Object_Collision(cell_membrane_octree_root, new_box);
     while(1){
         //crossing = check_crossing(proteinChannels_structure, new_box) || check_crossing(cell_proteins_structure, new_box);
         //if(crossing){
         if(AABB_overlaps){
            pos_x =  Math.random()*size_x;
           // pos_y =  Math.random()*size_y;
            pos_z =  Math.random()*size_z;
            new_box.center = [pos_x, size_y/2.0, pos_z];
            AABB_overlaps = false;
            //var crossing = false;
            check_AABB_Object_Collision(cell_membrane_octree_root, new_box);
            continue;
         }
         else break;
    }
    new_box.type = "protein";
    put_AABB_Object(cell_membrane_octree_root, new_box);
    cell_proteins_structure.push(new_box);
  }
}

function createLipids(size_x, size_z, pos_y, radius, nummber_of_lipids, type){
  //check for the intersection of the three lists.
   for(var i=0; i<nummber_of_lipids; i++){
     var pos_x;
     var pos_z;
     var new_box = new AABB();

     var curr_node =  getLowResolution_Octant_Lipids(cell_membrane_octree_root, 5, 1, pos_y);
     if(curr_node!=null){  //since we want to create a random point in the dimentions of this node
        pos_x = Math.random()*(curr_node.radius[0]*2);
        pos_z = Math.random()*(curr_node.radius[2]*2);
        pos_x = curr_node.center[0] + (pos_x - curr_node.radius[0]);
        pos_z = curr_node.center[2] + (pos_z - curr_node.radius[2]);
     }
     else{
         pos_x = Math.random()*size_x;
         pos_z = Math.random()*size_z;
     }
     new_box.center = [pos_x, pos_y, pos_z];
     new_box.radius = [radius, radius, radius];

     AABB_overlaps = false;
     var crossing = false;
     var new_box_volume = new_box.getVolume();
     curr_Intersection_Volume = 0;
     check_AABB_Object_Collision(cell_membrane_octree_root, new_box);
   var tried = 0;
   for(var j=0; j<lipids_structure.length && AABB_overlaps; j++){
        //if(overlapping_ratio<0.30)
        //   break;
        var curr_node = getLowResolution_Octant_Lipids(cell_membrane_octree_root, 5, 1, pos_y);
         var pos_x, pos_z;
         if(curr_node!=null){  //since we want to create a random point in the dimentions of this node
            pos_x = Math.random()*(curr_node.radius[0]*2);
            pos_z = Math.random()*(curr_node.radius[2]*2);
            pos_x = curr_node.center[0] + (pos_x - curr_node.radius[0]);
            pos_z = curr_node.center[2] + (pos_z - curr_node.radius[2]);
         }
         else{
             pos_x = Math.random()*size_x;
             pos_z = Math.random()*size_z;
         }
         new_box.center = [pos_x, pos_y, pos_z];
          new_box.radius = [radius, radius, radius];
         if(tried>25)
            break;
        AABB_overlaps = false;
        curr_Intersection_Volume = 0;
        new_box_volume = new_box.getVolume();
        check_AABB_Object_Collision(cell_membrane_octree_root, new_box);
  
        if(AABB_overlaps){
            var overlapping_ratio =  curr_Intersection_Volume/new_box.getVolume();
            if(overlapping_ratio>0.30){
                j = 0;
                tried++;
                continue;
            }
            else
                AABB_overlaps = false;
        }
         if(tried>25)
            break;
     }
     if(tried>25)
         continue;
     new_box.type = "lipid";
     put_AABB_Object(cell_membrane_octree_root, new_box);
     lipids_structure.push(new_box);
     var vertices  = [];
     var indexes   = [];
     for(var m=0; m<8; m++){
         var ver = new_box.getCorner(m);
         vertices.push(ver[0]);
         vertices.push(ver[1]);
         vertices.push(ver[2]);
     }
     indexes = [0,1 ,1,2, 2,3, 3,0, 4,5, 5,6, 6,7, 0,4, 1,5, 2,6, 3,7];
     var transform = g_pack.createObject('Transform');
     transform.parent = g_client.root;
     var shape = o3djs.debug.createLineShape(g_pack,
                                      o3djs.material.createConstantMaterial(g_pack,
                                                        g_colorViewInfo, [1, 0, 0, 1]),
                                                        vertices, indexes);
         //for(var i=0; i<shape_array.length; i++)
        transform.addShape(shape);
        transform.cull      = false;
        transform.visible   = true;
  }

}

function createLipidsTrans(){
     var cubeMaterial = createShadowColorMaterial([0.7, 0.5, 0, 0.8]);
     var sphere;
     if(lipids_structure.length>0)
        sphere = o3djs.primitives.createSphere(g_pack, cubeMaterial, lipids_structure[0].radius[0], 20, 20);     // The length of each side of the cube.
    for(var i=0; i<lipids_structure.length; i++){
           transform = g_pack.createObject('Transform');
           transform.parent = g_client.root;
           transform.cull      = false;
           transform.visible   = true;
           lipids_structure[i].translate = transform;
           //transform.addShape(h_Shape);
           var scale_const = parseFloat(lipids_structure[i].radius[0]);
           //transform.scale(scale_const, scale_const, scale_const);
           var trans = g_pack.getObjects("lip","o3d.Transform")[0];
           // assuming you found multiple transforms
          var shapes = trans.shapes;
             // now iterate through the shapes.
          var shape=null;
          for (var jj = 0; jj < shapes.length; ++jj)
              shape = shapes[jj];

           if(shape!=null)
                transform.addShape(shape);
           else
               transform.addShape(sphere);

          transform.translate(parseFloat(lipids_structure[i].center[0]), parseFloat(lipids_structure[i].center[1]), parseFloat(lipids_structure[i].center[2]));
          //var scale_const_X = 5.5*parseFloat(lipids_structure[i].radius[0]);
          //var scale_const_Y = 5.5*parseFloat(lipids_structure[i].radius[1]);
          //var scale_const_Z = 5.5*parseFloat(lipids_structure[i].radius[2]);
          //transform.scale(scale_const_X, scale_const_Y, scale_const_Z);
          transform.translate([0.0, 0.0, 0.0]);
          transform.scale([0.055, 0.055, 0.055]);
          if(lipids_structure[i].type=="lipid")
            transform.rotateX(-90*RADIAN_INV);
        else
            transform.rotateX(90*RADIAN_INV);
            
    }
}

function UpdateLipidsTrans(){
     for(var i=0; i<lipids_structure.length; i++){
         lipids_structure[i].translate.translate(0, 0, 0);
          lipids_structure[i].translate.translate(lipids_structure[i].center[0], lipids_structure[i].center[1], lipids_structure[i].center[2]);
          //lipids_structure[i].translate.visible = !lipids_structure[i].translate.visible;
    }
}
function UpdateLipidsTrans_Orgin(){
     for(var i=0; i<lipids_structure.length; i++){
         lipids_structure[i].translate.translate(-lipids_structure[i].center[0], -lipids_structure[i].center[1], -lipids_structure[i].center[2]);
          //lipids_structure[i].translate.visible = !lipids_structure[i].translate.visible;
    }
}

function createProtein_ChannalTrans(){
     var cubeMaterial = createShadowColorMaterial([0.7, 0.5, 0, 0.8]);
     var cube = o3djs.primitives.createCube(g_pack,cubeMaterial, 1);     // The length of each side of the cube.
     for(var i=0; i<proteinChannels_structure.length; i++){
           transform = g_pack.createObject('Transform');
           transform.parent = g_client.root;
          
           transform.cull      = false;
           transform.visible   = true;
           proteinChannels_structure[i].translate = transform;
          var trans = g_pack.getObjects("protein_channel_complete01","o3d.Transform")[0];
           // assuming you found multiple transforms
          var shapes = trans.shapes;
          //alert("Shapes_Lenght: " + shapes.length);
         // now iterate through the shapes.
          var shape;
          for (var jj = 0; jj < shapes.length; ++jj) 
              shape = shapes[jj];       

         if(shape!=[])
                transform.addShape(shape);
            else
                transform.addShape(cube);

          transform.translate(parseFloat(proteinChannels_structure[i].center[0]), parseFloat(proteinChannels_structure[i].center[1]) + proteinChannels_structure[i].radius[1]/2.0, parseFloat(proteinChannels_structure[i].center[2]));
         // var scale_const_X = 2*parseFloat(proteinChannels_structure[i].radius[0]);
          //var scale_const_Y = 2*parseFloat(proteinChannels_structure[i].radius[1]);
          //var scale_const_Z = 2*parseFloat(proteinChannels_structure[i].radius[2]);
          //transform.scale(scale_const_X, scale_const_Y, scale_const_Z);
          transform.translate([0.0, 0.0, 0.0]);
          transform.scale([0.03, 0.04, 0.03]);
          transform.rotateX(90*RADIAN_INV);

     }

}

function createProteinsTrans(){

     var cubeMaterial = createShadowColorMaterial([0.0, 0.5, 1.0, 0.8]);
     var cube = o3djs.primitives.createCube(g_pack,cubeMaterial, 1);     // The length of each side of the cube.
     for(var i=0; i<cell_proteins_structure.length; i++){
           transform = g_pack.createObject('Transform');
           transform.parent = g_client.root;
           transform.cull      = false;
           transform.visible   = true;
           cell_proteins_structure[i].translate = transform;
           //transform.addShape(cube);

          var trans = g_pack.getObjects("prot","o3d.Transform")[0];
           // assuming you found multiple transforms
          var shapes = trans.shapes;
             // now iterate through the shapes.
          var shape;
          for (var jj = 0; jj < shapes.length; ++jj)
              shape = shapes[jj];

          if(shape!=[])
                transform.addShape(shape);
           else
                transform.addShape(cube);

          transform.translate(parseFloat(cell_proteins_structure[i].center[0]), parseFloat(cell_proteins_structure[i].center[1]), parseFloat(cell_proteins_structure[i].center[2]));
          //var scale_const_X = 2*parseFloat(cell_proteins_structure[i].radius[0]);
          //var scale_const_Y = 2*parseFloat(cell_proteins_structure[i].radius[1]);
          //var scale_const_Z = 2*parseFloat(cell_proteins_structure[i].radius[2]);
          //transform.scale(scale_const_X, scale_const_Y, scale_const_Z);
          transform.translate([0.0, 0.0, 0.0]);
          transform.scale([0.04, 0.04, 0.04]);
          transform.rotateX(90*RADIAN_INV);
     }
 }


 function loadFile(context, filename) {
  function callback_loader(pack, parent, exception) {
    //enableInput(true);
    if (exception) {
      alert("Could not load: " + filename + "\n" + exception);
    } else {
      // Generate draw elements and setup material draw lists.
      o3djs.pack.preparePack(pack, g_colorViewInfo);
      var bbox = o3djs.util.getBoundingBoxOfTree(g_client.root);
      var diag = g_math.length(g_math.subVector(bbox.maxExtent,
                                                bbox.minExtent));
      // Manually connect all the materials' lightWorldPos params to the context
      var materials = pack.getObjectsByClassName('o3d.Material');
      for (var m = 0; m < materials.length; ++m) {
        var material = materials[m];
        var param = material.getParam('lightWorldPos');
        if (param) {
          //param.bind(g_lightWorldPos);
        }
      }

      g_finished = true;  // for selenium
      all_finished++;

      // Comment out the next line to dump lots of info.
      if (false) {
        o3djs.dump.dump('---dumping context---\n');
        o3djs.dump.dumpParamObject(context);

        o3djs.dump.dump('---dumping root---\n');
        o3djs.dump.dumpTransformTree(g_client.root);

        o3djs.dump.dump('---dumping render root---\n');
        o3djs.dump.dumpRenderNodeTree(g_client.renderGraphRoot);

        o3djs.dump.dump('---dump g_pack shapes---\n');
        var shapes = pack.getObjectsByClassName('o3d.Shape');
        for (var t = 0; t < shapes.length; t++) {
          o3djs.dump.dumpShape(shapes[t]);
          alert("There is a shape");
        }

        o3djs.dump.dump('---dump g_pack materials---\n');
        var materials = pack.getObjectsByClassName('o3d.Material');
        for (var t = 0; t < materials.length; t++) {
          o3djs.dump.dump (
              '  ' + t + ' : ' + materials[t].className +
              ' : "' + materials[t].name + '"\n');
          o3djs.dump.dumpParams(materials[t], '    ');
        }

        o3djs.dump.dump('---dump g_pack textures---\n');
        var textures = pack.getObjectsByClassName('o3d.Texture');
        for (var t = 0; t < textures.length; t++) {
          o3djs.dump.dumpTexture(textures[t]);
       }

        o3djs.dump.dump('---dump g_pack effects---\n');
        var effects = pack.getObjectsByClassName('o3d.Effect');
        for (var t = 0; t < effects.length; t++) {
          o3djs.dump.dump ('  ' + t + ' : ' + effects[t].className +
                  ' : "' + effects[t].name + '"\n');
          o3djs.dump.dumpParams(effects[t], '    ');
        }
      }
    }
  }

  var scenePath = o3djs.util.getCurrentURI() + filename;
  //var obj_pack = g_client.createPack();
  //Create a new transform for the loaded file
  var transform = g_pack.createObject('Transform');
  transform.parent = g_client.root;
    try {
      var load_info = o3djs.scene.loadScene(g_client, g_pack, transform, scenePath, callback_loader);
      alert("Load_INFO: " + load_info.getKnownProgressInfoSoFar());
    } catch (e) {
      alert("could not load the 3d object");
    }
 
  return transform;
}


function execute_Lennard__Jones_Step(size_x, size_z){
  UpdateLipidsTrans_Orgin();
  Lennard_Jones_Dynamics(4, 4, 11, 0.1, [-5, size_x+3], [-5, size_z+3]);
  UpdateLipidsTrans();
}


function createMembrane(size_x, size_z, size_y){

    lipids_structure = [];
    proteinChannels_structure = [];
    cell_proteins_structure = [];
    var window_loc = giveWindowLoc();
    if(cell_membrane_protein_object==null){
       cell_membrane_protein_object = loadFile(g_colorViewInfo.drawContext, "assets/prot_inv.o3dtgz");
        cell_membrane_protein_object.translate([0.0, 0.0,0.0]);
        cell_membrane_protein_object.scale([0.04, 0.04, 0.04]);
        cell_membrane_protein_object.rotateX(90*RADIAN_INV);
    }
    if(cell_membrane_channel_object==null){
        cell_membrane_channel_object = loadFile(g_colorViewInfo.drawContext, "assets/protein_channel_cpl.o3dtgz");
        cell_membrane_channel_object.translate([0.0, 0.0,0.0]);
        cell_membrane_channel_object.scale([0.04, 0.04, 0.04]);
        cell_membrane_channel_object.rotateX(90*RADIAN_INV);
    }
    if(cell_membrane_half_channel_object==null){
        cell_membrane_half_channel_object = loadFile(g_colorViewInfo.drawContext, "assets/protein_channel_section.o3dtgz");
        cell_membrane_half_channel_object.translate([0.0, 0.0,0.0]);
        cell_membrane_half_channel_object.scale([0.04, 0.04, 0.04]);
        cell_membrane_half_channel_object.rotateX(90*RADIAN_INV);
    }
    if(cell_membrane_lipid_object==null){
        cell_membrane_lipid_object = loadFile(g_colorViewInfo.drawContext, "assets/lipid.o3dtgz");
        cell_membrane_lipid_object.translate([0.0, 0.0,0.0]);
        cell_membrane_lipid_object.scale([0.04, 0.04, 0.04]);
        cell_membrane_lipid_object.rotateX(90*(RADIAN_INV));
    }
    if(cell_membrane_lipid_object_reverted==null)
    {
        cell_membrane_lipid_object_reverted = loadFile(g_colorViewInfo.drawContext, "assets/lipid.o3dtgz");
        cell_membrane_lipid_object_reverted.translate([0.0, 0.0,0.0]);
        cell_membrane_lipid_object_reverted.scale([0.04, 0.04, 0.04]);
        cell_membrane_lipid_object_reverted.rotateX(-90*(RADIAN_INV));
    }

   // cell_membrane_object = loadFile(g_colorViewInfo.drawContext, "assets/membrane_normals.o3dtgz");

    //var i = 0;
    //while(all_finished<4)
    //    i++;
   cell_membrane_octree_root = new scene_octree([size_x/2.0, size_y/2.0, size_z/2.0], [size_x/2.0 + 2, size_y/2.0+2, size_z/2.0+2], 0);
    createScene_Octree(cell_membrane_octree_root, 5);
   // calc_Tree_Volume(cell_membrane_octree_root);            //calculate the volume of each node in the octree;

    createProtein_Channal(size_x, size_z, size_y/2.0, 20);
    createProteins(size_x, size_y, size_z, 15);
    createLipids(size_x, size_z, size_y, 0.2, 600, "lipid");
    //Lennard_Jones_Dynamics(4, 4, 50, 0.001, [-5, size_x+3], [-5, size_z+3]);
    createLipids(size_x, size_z, 0, 0.2, 600, "lipid_reversed");
    mergeNodes(cell_membrane_octree_root, 10);
    createLipidsTrans();
    createProtein_ChannalTrans();
    createProteinsTrans();
    /*
    var z_dir = gCamera.look;
    var y_dir = gCamera.up;
    var x_dir = crossProduct(y_dir, z_dir);
    x_dir = normalizeVec3D(x_dir);
    var cam_frame = [x_dir, y_dir, z_dir];
    
    find_visibility(cell_membrane_octree_root, gCamera.position, cam_frame, near, far, g_client.width/g_client.height, view_angle);
    */
}