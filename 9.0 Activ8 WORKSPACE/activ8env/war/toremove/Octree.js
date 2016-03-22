
var allLeafs = []; //holds the leafs of the octree
var crossingOctants = []; //holds all the crossing leaf octants with a defined sphere;
var subtree_point_count = 0;  //total point number in a sub tree

function Octree(c, r, curr_level){

    this.center = c;
    this.radius  = r;

    this.level   = curr_level;
    this.atom_IDS = [];
    this.max_level = 0;
    this.array_index = -1;
    this.parent    = null;
    this.childs = [null, null, null, null, null, null, null, null];

    this.getParent = function(){
        return this.parent;
    }
    this.getChild = function(index){
        if(index>=8)
            return null;
        return this.childs[index];
    }
    this.getMax = function(){
        return [this.center[0] + this.radius[0], this.center[1] + this.radius[1], this.center[2] + this.radius[2]];
    }
    this.getMin = function(){
        return [this.center[0] - this.radius[0], this.center[1] - this.radius[1], this.center[2] - this.radius[2]];;
    }
    this.getLevel = function(){
        return this.level;
    }
    this.getArr_Index = function(){
        return this.array_index;
    }
}

//Creates a new view matrix
function createTree(node, max_level){

    if(node==null)
        return;
    if(max_level==node.getLevel()){
        return;
    }

    var lev = node.getLevel();
    var new_rad = divPoint(node.radius, 2);

    var first  = new Octree([node.center[0] - new_rad[0], node.center[1] - new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var second = new Octree([node.center[0] + new_rad[0], node.center[1] - new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var third  = new Octree([node.center[0] + new_rad[0], node.center[1] - new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var fourth = new Octree([node.center[0] - new_rad[0], node.center[1] - new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var fifth  = new Octree([node.center[0] - new_rad[0], node.center[1] + new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var sixth  = new Octree([node.center[0] + new_rad[0], node.center[1] + new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var seventh= new Octree([node.center[0] + new_rad[0], node.center[1] + new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var eight  = new Octree([node.center[0] - new_rad[0], node.center[1] + new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var children = [first, second, third, fourth, fifth, sixth, seventh, eight];

    for(var i=0; i<8; i++){
         node.childs[i] = children[i];
         node.childs[i].parent = node;
    }
    
    if(max_level==node.getLevel()+1){
      var len = allLeafs.length;
      for(var i=0; i<8; i++)
         allLeafs.push(node.childs[i]);
     for(var i=0; i<8; i++)
         node.childs[i].array_index = len + i;
      return;
    }
    for(var i=0; i<8; i++)
        createTree(node.childs[i], max_level);

    return;
}


function putPoint(node, x, y, z, atomID){

   x = Number(x);
   y = Number(y);
   z = Number(z);
   atomID = Number(atomID);
   if(node==null)
       return;
   if(node.childs[0]==null){
       var max_pnt = [node.center[0] + node.radius[0], node.center[1] + node.radius[1], node.center[2] + node.radius[2]];
       var min_pnt = [node.center[0] - node.radius[0], node.center[1] - node.radius[1], node.center[2] - node.radius[2]];
       if((min_pnt[0]<=x && x<=max_pnt[0]) && (min_pnt[1]<=y && y<=max_pnt[1]) && (min_pnt[2]<=z && z<=max_pnt[2])){
          node.atom_IDS.push(atomID);
          return;
       }
       else
           return;
   }

   for(var i=0; i<8; i++){
      if((Number(node.childs[i].center[0]- node.childs[i].radius[0])<=x && x<=Number(node.childs[i].center[0] + node.childs[i].radius[0])) &&
      (Number(node.childs[i].center[1]- node.childs[i].radius[1])<=y && y<=Number(node.childs[i].center[1] + node.childs[i].radius[1])) &&
      (Number(node.childs[i].center[2]- node.childs[i].radius[2])<=z && z<=Number(node.childs[i].center[2] + node.childs[i].radius[2])))
            putPoint(node.childs[i], x,y,z, atomID);
   }
   return;
}


function getPoint_Number(node){

    if(node==null)
        return;
    else if(node.getChild(0)==null && node.getChild(1)==null && node.getChild(2)==null && node.getChild(3)==null
            && node.getChild(4)==null && node.getChild(5)==null && node.getChild(6)==null && node.getChild(7)==null){  //if the node is a leaf node

                subtree_point_count += node.atom_IDS.length;
                return;
            }
    for(var i=0; i<8; i++){
       if(node.getChild(i)!=null)
         getPoint_Number(node.getChild(i));
    }
}

function mergeTree(index, threshold) {

    for(var i=0; i<allLeafs.length; i++){

        if(allLeafs[i]==null)
            continue;

        subtree_point_count = 0;
        parent_Node = allLeafs[i].getParent(); //get the parent of the child.
        getPoint_Number(parent_Node);  //get  the total points in the subtree
      
   
        if(subtree_point_count<threshold){

            for(var j=0; j<8; j++){
                //if(parent_Node.childs[j]==null)
                //    continue;
                if(parent_Node.childs[j].atom_IDS.length!=0)
                    parent_Node.atom_IDS = parent_Node.atom_IDS.concat(parent_Node.childs[j].atom_IDS);
            }
            for(var j=0; j<8; j++){
               if(parent_Node.childs[j]==null)
                    continue;
               allLeafs[parent_Node.childs[j].getArr_Index()] = null;
            }
            
            for(var j=0; j<8; j++){
                 if(parent_Node.childs[j]==null)
                    continue;
                delete parent_Node.childs[j];
            }
            
            parent_Node.childs = [null, null, null, null, null, null, null, null];
            allLeafs.push(parent_Node);
            parent_Node.array_index = allLeafs.length-1;
        }
    }

    var allLeaves_temp = [];
    for(i=0; i<allLeafs.length;i++ ){
        if(allLeafs[i]!=null)
           allLeaves_temp.push(allLeafs[i]);
    }
    delete allLeafs;
    allLeafs = allLeaves_temp;
    delete allLeaves_temp;
    for(i=0; i<allLeafs.length; i++){
        allLeafs[i].array_index = i;
    }
}

function findCrossingOctants(radius, x, y, z, octant){

  if(octant==null)
      return;
  var closest_x=0;
  var closest_y=0;
  var closest_z=0;
   
  x = Number(x);
  y = Number(y);
  z = Number(z);
  radius = Number(radius);


  if(x<octant.center[0] - octant.radius[0])
      closest_x = octant.center[0] - octant.radius[0];
  else if(x>octant.center[0] + octant.radius[0])
      closest_x = octant.center[0] + octant.radius[0];
  else
      closest_x = x;

  if(y<octant.center[1] - octant.radius[1])
      closest_y = octant.center[1] - octant.radius[1];
  else if(y>octant.center[1] + octant.radius[1])
      closest_y = octant.center[1] + octant.radius[1];
  else
      closest_y = y;

  if(z<octant.center[2] - octant.radius[2])
      closest_z = octant.center[2] - octant.radius[2];
  else if(z>octant.center[2] + octant.radius[2])
      closest_z = octant.center[2] + octant.radius[2];
  else
      closest_z = z;

  var dist = Math.sqrt(Math.pow(closest_x - x, 2.0) + Math.pow(closest_y - y, 2.0) + Math.pow(closest_z - z, 2.0));

  if(octant.childs[0]==null && (dist<=radius)){
      crossingOctants.push(octant);
      return;
  }

  for(var i=0; i<8; i++){
    if(octant.childs[i]!=null && (dist<=radius))
        findCrossingOctants(radius, x, y, z, octant.childs[i]);
  }
}
