o3djs.require('o3djs.util');
o3djs.require('o3djs.math');
o3djs.require('o3djs.rendergraph');
o3djs.require('o3djs.primitives');
o3djs.require('o3djs.material');
o3djs.require('o3djs.pack');
o3djs.require('o3djs.scene');
o3djs.require('o3djs.debug');

var PI =  3.14159265358;
var all_PDB_Files = [];
var lightPos   = [];
var lightColor = [];
var atomTypes;
var alpha_helix_type = 1;
var beta_sheet_type  = 2;

var existing_bonds = [];

 var positionArrayC = [
    -0.5, -0.5,  0.5,  // vertex 0
     0.5, -0.5,  0.5,  // vertex 1
    -0.5,  0.5,  0.5,  // vertex 2
     0.5,  0.5,  0.5,  // vertex 3
    -0.5,  0.5, -0.5,  // vertex 4
     0.5,  0.5, -0.5,  // vertex 5
    -0.5, -0.5, -0.5,  // vertex 6
     0.5, -0.5, -0.5   // vertex 7
  ];

  // The following array defines how vertices are to be put together to form
  // the triangles that make up the cube's faces.  In the index array, every
  // three elements define a triangle.  So for example vertices 0, 1 and 2
  // make up the first triangle, vertices 2, 1 and 3 the second one, etc.
  var indicesArrayC = [
      0, 1, 2,  // face 1
      2, 1, 3,
      2, 3, 4,  // face 2
      4, 3, 5,
      4, 5, 6,  // face 3
      6, 5, 7,
      6, 7, 0,  // face 4
      0, 7, 1,
      1, 7, 3,  // face 5
      3, 7, 5,
      6, 0, 4,  // face 6
      4, 0, 2
  ];



var Hermit_M = [[2.0, -2.0, 1.0, 1.0], [-3.0, 3.0, -2.0, -1.0], [0, 0, 1, 0], [1, 0, 0, 0]];

var importedPDB_Files = [];


function findPoint(points, t_val){

        //http://www.mvps.org/directx/articles/catmull/
        var t_val_2 = Math.pow(t_val, 2);
        var t_val_3 = Math.pow(t_val, 3);

        var theta = 1;
        var first = -1*t_val*theta + 2*t_val_2*theta - theta*t_val_3;
        var second= 1 + t_val_2*theta - 3*t_val_2 + 2*t_val_3 - theta*t_val_3;
        var third = theta*t_val + 3*t_val_2 - 2*theta*t_val_2 + t_val_3*theta - 2*t_val_3;
        var fourth= -1*theta*t_val_2 + theta*t_val_3;

        var x = first*points[0][0] + second*points[1][0] + third*points[2][0] + fourth*points[3][0];
        var y = first*points[0][1] + second*points[1][1] + third*points[2][1] + fourth*points[3][1];
        var z = first*points[0][2] + second*points[1][2] + third*points[2][2] + fourth*points[3][2];
        return [x,y,z];
}


function findPoint_Hermite(Points, t_val){

        var t_val_2 = Math.pow(t_val, 2);
        var t_val_3 = Math.pow(t_val, 3);

        var result = [0,0,0];
        result[0] = 2*Points[0][0]*t_val_3 - 3*Points[0][0]*t_val_2 + Points[0][0] - 2*Points[1][0]*t_val_3 + 3*Points[1][0]*t_val_2
                    + Points[2][0]*t_val_3 - 2*Points[2][0]*t_val_2 + Points[2][0] + Points[3][0]*t_val_3 - Points[3][0]*t_val_2;

         result[1] = 2*Points[0][1]*t_val_3 - 3*Points[0][1]*t_val_2 + Points[0][1] - 2*Points[1][1]*t_val_3 + 3*Points[1][1]*t_val_2
                    + Points[2][1]*t_val_3 - 2*Points[2][1]*t_val_2 + Points[2][1] + Points[3][1]*t_val_3 - Points[3][1]*t_val_2;

         result[2] = 2*Points[0][2]*t_val_3 - 3*Points[0][2]*t_val_2 + Points[0][2] - 2*Points[1][2]*t_val_3 + 3*Points[1][2]*t_val_2
                    + Points[2][2]*t_val_3 - 2*Points[2][2]*t_val_2 + Points[2][2] + Points[3][2]*t_val_3 - Points[3][2]*t_val_2;
         return result;
}


function multiplyPnt(pnt, constant){
	var result = [];
	return [pnt[0]*constant, pnt[1]*constant, pnt[2]*constant];
}

function crossProduct(pnt1, pnt2){

	return [pnt1[1]*pnt2[2] - pnt1[2]*pnt2[1], pnt1[2]*pnt2[0] - pnt1[0]*pnt2[2], pnt1[0]*pnt2[1] - pnt2[0]*pnt1[1]];
}

function dotProduct(pnt1, pnt2){
	return pnt1[0]*pnt2[0] + pnt1[1]*pnt2[1] + pnt1[2]*pnt2[2];
}

function normalizeVec3D(vec){

	var length_vec = Math.sqrt(Math.pow(vec[0], 2.0) + Math.pow(vec[1], 2.0) + Math.pow(vec[2], 2.0));
	var unit_vec = [];
	unit_vec[0] = vec[0]/length_vec;
	unit_vec[1] = vec[1]/length_vec;
	unit_vec[2] = vec[2]/length_vec;

	return unit_vec;
}

function addPoints(pnt1, pnt2){
	return [pnt1[0]+pnt2[0], pnt1[1]+pnt2[1], pnt1[2]+pnt2[2]];
}

function subPoints(pnt1, pnt2){
	return [pnt1[0]-pnt2[0], pnt1[1]-pnt2[1], pnt1[2]-pnt2[2]];
}

function divPoint(pnt, constant){
	var k = 1.0/constant;
        return [pnt[0]*k, pnt[1]*k, pnt[2]*k];
}

function distance(pnt1, pnt2){
	var pow_x = Math.pow(pnt1[0] - pnt2[0], 2);
        var pow_y = Math.pow(pnt1[1] - pnt2[1], 2);
        var pow_z = Math.pow(pnt1[2] - pnt2[2], 2);
        return Math.sqrt(pow_x + pow_y + pow_z);
}

function length(pnt){
    var sum = 0;
    for(var i=0; i<pnt.length; i++)
        sum += Math.pow(pnt[i], 2);
    return Math.sqrt(sum);    
}

function getDegree(vector_1, vector_2){

   var dot_vectors = dotProduct(vector_1, vector_2);
   var m = (dot_vectors/(length(vector_1)*length(vector_2)));
   var normal_degrees = Math.acos(m)*(180.0/Math.PI);
   return normal_degrees;
   
   
}

function between(pnt, point_1, point_2){

    if(pnt[0]>point_1[0] && pnt[0]>point_2[0])
        return false;
    else if(pnt[0]<point_1[0] && pnt[0]<point_2[0])
        return false;
    if(pnt[1]>point_1[1] && pnt[1]>point_2[1])
        return false;
    else if(pnt[1]<point_1[1] && pnt[1]<point_2[1])
        return false;
    if(pnt[2]>point_1[2] && pnt[2]>point_2[2])
        return false;
    else if(pnt[2]<point_1[2] && pnt[2]<point_2[2])
        return false;
   return true;
}

function swap(first, second){
    var temp = first;
    first = second;
    second = first;
}

function point3D(id){
	this.ID = id;
	this.atomID= -1;
        this.type  = 0;   //unknown=0, alpha helix point type = 1, beta sheet point type = 2
        this.last_four = false;
        this.info = "";
    this.Point = [];
}

point3D.prototype.getID = function(){
	return this.ID;
}

point3D.prototype.setID = function(id){
	this.ID = id;
}

point3D.prototype.getatomID = function(){
	return atomID;
}

point3D.prototype.setatomID = function(id){
	this.atomID = id;
}

point3D.prototype.getCoord = function(){
	return this.Point;
}

point3D.prototype.setCoord = function(coord){
	this.Point = coord;
}

function isNumber(sText)
{
   var ValidChars = "0123456789.-";
   for (var i = 0; i < sText.length; i++)
   {
      if (ValidChars.indexOf(sText.charAt(i)) < 0)
		 return false;
   }
   return true;
}


function Circle(){
	this.ID = -1;
        this.type = 0; // 0=spaghetti, 1=a-helix, 2=b-sheet
	this.total_pnt = 0;
	this.points  = [];
	this.center = [];
	this.nextPoint = [];
        this.prev_Point = [];
	this.normal = [];
	this.radius = 0;
        this.info   = "";
        this.last_four = false;    // It is for beta sheets that, if it is on of the last four circles on the
                                   // beta sheet, it's radius has to be decreased to have like arrow shape beta sheets.
	this.getRadius = function()    {return this.radius;}
	this.setRadius = function(rad) {this.radius = rad;}
	this.getCenter = function()    {return this.center;}
	this.setCenter = function(cen)    {
                        if(cen.length==3)
                                this.center = cen;
        }
	this.getTotalPoints = function() {return this.total_pnt;}

	this.setNextPoint = function(n_pnt) {
			//if(n_pnt.lenght==3)
                            this.nextPoint = n_pnt;
        }
	this.getNextPoint = function() {return this.nextPoint;}

	this.getResolution = function() {return this.points.length;}
	this.getCirclePnt  = function(id) {
		if(this.getResolution()>id )
			return this.points[id];
		return null;
	}

	this.calc_CirclePoints = function(resolution, type){

		 var inc_k = 1.0/resolution;
		 var curr_ID = 0;
		 var vector_W = [this.nextPoint[0] - this.center[0], this.nextPoint[1] - this.center[1], this.nextPoint[2] - this.center[2]];
		 vector_W = normalizeVec3D(vector_W);
                 this.normal = [vector_W[0], vector_W[1], vector_W[2]];
                 var vector_U = [0,0,0];


                 var vector_V = [0,0,0];
                 if((Math.abs(vector_W[0]) >= Math.abs(vector_W[1]))){
                    factor = 1/Math.sqrt(vector_W[0]*vector_W[0]+vector_W[2]*vector_W[2]);
                    vector_U[0] = (-vector_W[2])*factor;
                    vector_U[1] = 0;
                    vector_U[2] = (vector_W[0])*factor;
                    vector_U = normalizeVec3D(vector_U);
                 }
                 else if((Math.abs(vector_W[0]) < Math.abs(vector_W[1]))){
                    factor = 1/Math.sqrt(vector_W[1]*vector_W[1]+vector_W[2]*vector_W[2]);
                    vector_U[0] = 0
                    vector_U[1] = (vector_W[2])*factor;;
                    vector_U[2] = (-vector_W[1])*factor;
                    vector_U = normalizeVec3D(vector_U);
                 }
                    
                 vector_V = crossProduct(vector_W, vector_U);
                 vector_V = normalizeVec3D(vector_V);

		 //var vector_U = [0,0,0];
		 

                 var curr_radian = 0;
                 var Two_PI      = 2*PI
                 var incr_radian = Two_PI/resolution;
                 var factor = 0;
                 for(;curr_radian<Two_PI;){

             
                     var m = this.getRadius()*Math.cos(curr_radian);
                     var n = (this.getRadius())*Math.sin(curr_radian);

                     m = multiplyPnt(vector_U, m);
                     n = multiplyPnt(vector_V, n);
                     var newPnt = addPoints(m,n);
                     newPnt = addPoints(newPnt, this.getCenter());

                      var newPoint = new point3D();
                      newPoint.setCoord(newPnt);
                      newPoint.setID(curr_ID);
		      this.points.push(newPoint);
		      this.total_pnt++;

                     curr_radian += incr_radian;
                 }
	}
        this.reorder_CirclePoints = function(previous_circle){

            var vector_W = [this.center[0]-previous_circle.center[0], this.center[1]-previous_circle.center[1], this.center[2]-previous_circle.center[2]];
            //var new_order = [];

            for(var i=0; i<previous_circle.points.length; i++){
                var previous_pnt = previous_circle.points[i].getCoord();
                previous_pnt = [vector_W[0] + previous_pnt[0], vector_W[1] + previous_pnt[1], vector_W[2] + previous_pnt[2]];
                this.points[i].Point = [previous_pnt[0], previous_pnt[1], previous_pnt[2]];
            }
        }
        this.makecircle_Elipse = function(inner_rad){
            var Two_PI = 2*PI
            var radian = Math.PI*2/this.points.length;
            //for(var i=0; i<=this.points.length-1; i++){
            var k=0;
            var a_b = this.radius*inner_rad;
            for (var i = 0; i < 360; i += 360.0 / this.points.length, k++){
                var alpha = i * (Math.PI / 180) ;
                var sinalpha = Math.sin(alpha);
                var cosalpha = Math.cos(alpha);
                var b_cos = inner_rad*cosalpha;
                var a_sin = this.radius*sinalpha;
                var dist = Math.sqrt(b_cos*b_cos + a_sin*a_sin);;
                dist = a_b/dist;
               
                var diff_pnts = subPoints(this.points[k].Point, this.center);
                var mult = dist/this.radius;
                var unit = multiplyPnt(diff_pnts, mult);

                //var extent = multiplyPnt(unit, dist);
                this.points[k].Point = addPoints(this.center, unit);
            }
         /*   var pnt = addPoints(this.points[0].Point, this.points[this.points.length-1].Point);
            pnt = multiplyPnt(pnt, 0.5);
            this.points[0].Point = pnt;
            var pnt = addPoints(this.points[parseInt(this.points.length/2)-2].Point, this.points[parseInt(this.points.length/2)].Point);
            pnt = multiplyPnt(pnt, 0.5);
            this.points[parseInt(this.points.length/2)-1].Point = pnt;
            */
        }

        this.half_size = function(factor){
            if(factor<0){
                 var t = 2+(factor/4.0);
                  var res = this.points.length;
                    for(var i=0; i<res; i++){
                        var pnt = this.points[i].Point;
                        pnt = subPoints(pnt, this.center);
                        pnt = multiplyPnt(pnt, t);
                        pnt = addPoints(pnt, this.center);
                        this.points[i].Point = pnt;
                    }
            }
            else
                for(var t=0; t<factor; t++){
                    var res = this.points.length;
                    for(var i=0; i<res; i++){
                        var pnt = this.points[i].Point;
                        pnt = addPoints(pnt, this.center);
                        pnt = multiplyPnt(pnt, 0.5);
                        this.points[i].Point = pnt;
                    }
               }
        }
}


function stick(){
    this.start_atom_name = "";
    this.end_atom_name   = "";
    this.material        = [];
    this.shape           = null;
}



function resedue(){
	var ID;
	var Name;
	var atomsID =[];
}


function backBoneStructure(){
	this.ID = -1;
	this.backBoneName = "";
	this.circle_resolution = 0;
	this.effect_file_path = "";
	this.shader_str = "";
	this.atomsID = [];
	this.point3D = [];
	this.Circles = [];

	this.vertexArray = [];  //Half edge vertex arrays
	this.edgeArray = [];    //Half edge struxtures' edge array
	this.faceArray = [];    //Half edge structures' face array
        this.edgeArray = [];    //holds all the edges in the backbone

	this.indexArray = [];
	this.posArray   = [];
        this.spaghetti_cylinder_trans = [];
       
        this.pdb_data = null;
        this.vertex_Infos = [];
        this.transforms   = [];
        this.spaghetti_vertex_infos = [];
        this.helixes_vertex_infos   = [];
        this.sheets_vertex_infos    = [];
        
        this.curves_vertex_Infos = []; // [...], [...], [...] holds the backbone curve structures: alpha_helix_1, b_sheet_1, spagetti_1, helix_2 ....
        this.curves_types        = []; // 0, 1, 2, 0 , 1, 0, 2, ...
	this.getCircle = function(id){

		if(id<this.Circles.length)
			return this.Circles[id];
	   return null;
	}

	this.edgeExists = function(edge){
		var first_ID = edge.vertex_IDs[0];
		var second_ID= edge.vertex_IDs[1];

		for(var i=0; i<this.edgeArray.length; i++){
			if(this.edgeArray[i].vertex_IDs[0]==first_ID && this.edgeArray[i].vertex_IDs[1]==second_ID)
				return true;
			else if(this.edgeArray[i].vertex_IDs[1]==first_ID && this.edgeArray[i].vertex_IDs[0]==second_ID)
				return true;
		}
		return false;
	}
}



backBoneStructure.prototype.constructCurvePoints = function(atom_array, steps){
	//alert("Hebeleme");
	delete this.point3D;
	this.point3D = [];
	var P1 = [];
	var P2 = [];
        var P3 = [];
        var P4 = [];
	var T1 = []; //Ti = 0.5*(Pi+1 - Pi)
	var T2 = []; //If P is an end point for P0, you can define P'0 like P'0 = (1/2)*(2*P1 - P2 - P0)

	var p1_ID;
	var p2_ID;
	var p3_ID;
	var p4_ID;
        var step_length = 1.0/steps;
	//alert("inside constructCurve");
	for(var i=0; i<this.atomsID.length-2; i++){


		if(i==0){
			p1_ID = this.atomsID[0];
			p2_ID = this.atomsID[0];
			p3_ID = this.atomsID[1];
			p4_ID = this.atomsID[2];

		}
		else if(i==this.atomsID.length-3){
			p1_ID = this.atomsID[i-1];
			p2_ID = this.atomsID[i];
			p3_ID = this.atomsID[i+1];
			p4_ID = this.atomsID[i+1];
		}
		else{
                        p1_ID = this.atomsID[i-1];
			p2_ID = this.atomsID[i];
			p3_ID = this.atomsID[i+1];
			p4_ID = this.atomsID[i+2];
		}

		P1 = atom_array[p1_ID-1].getCoord();
		P2 = atom_array[p2_ID-1].getCoord();
		P3 = atom_array[p3_ID-1].getCoord();
                P4 = atom_array[p4_ID-1].getCoord();

               // T1 = atom_array[p3_ID-1].getCoord();
		//T2 = atom_array[p4_ID-1].getCoord();


		var pointM = [];
                pointM.push(P1);
                pointM.push(P2);
                pointM.push(P3);
                pointM.push(P4);

		for(var t=0; t<0.99;){
			var MB = findPoint(pointM, t);

			//var result = o3djs.math.rowMajor.mulMatrixMatrix(t:matrix, MB); //this sould be a 3D coordinate
			var newPoint = new point3D(this.point3D.length);
			newPoint.setCoord(MB);

                       newPoint.type = this.pdb_data.atoms[this.atomsID[i]-1].secondry_type;
                       if(newPoint.type==2 && (this.pdb_data.atoms[ this.atomsID[i]-1].secondry_type!=this.pdb_data.atoms[this.atomsID[i+1]-1].secondry_type)){                           
                           var last_four = t + step_length*4;
                           if(last_four>=1.0)
                                newPoint.last_four = true;
                        }
                        
		       if(t==0.0)
			    newPoint.setatomID(this.atomsID[i]);
			else if(t==1.0)
				newPoint.setatomID(this.atomsID[i+1]);
                        if(this.pdb_data!=null){
                            newPoint.type = this.pdb_data.atoms[this.atomsID[i]-1].secondry_type;
                        }
                        if(this.pdb_data.atoms[this.atomsID[i]-1].secondry_type==2)
                            this.pdb_data.atoms[this.atomsID[i]-1].secondry_type = 2;

                        newPoint.info = this.pdb_data.atoms[this.atomsID[i]-1].resedueName + " " + this.pdb_data.atoms[this.atomsID[i]-1].chainName;
			this.point3D.push(newPoint);
			t += step_length;
		}
                this.point3D.pop();
	}
        //for(var i=1; i!=this.point3D.length-1; i++){

        //    this.point3D[i].Point = [i, 5, 0];
       // }
	for(var i=1; i!=this.point3D.length-1; i++){

		var newCircle = new Circle();
		newCircle.setCenter(this.point3D[i].getCoord());
		newCircle.setNextPoint(this.point3D[i+1].getCoord());
                newCircle.prev_Point = this.point3D[i-1].getCoord();
		newCircle.setRadius(1.0);
		newCircle.ID = i;
		newCircle.calc_CirclePoints(20, 1);
                newCircle.type = this.point3D[i].type;
                newCircle.last_four = this.point3D[i].last_four;
		this.Circles.push(newCircle);
                newCircle.info = this.point3D[i].info;
                this.point3D[i].info = "";
                if(i>1)
                    this.reorder_points(this.Circles[this.Circles.length-2], this.Circles[this.Circles.length-1]);
               // if(i>1)
               //     this.Circles[i-1].reorder_CirclePoints(this.Circles[i-2]);
	}
        for(var i=0; i<this.Circles.length; i++){

            var resizing_factor = 1;

            if(this.Circles[i].type==0){
                this.Circles[i].half_size(4);
                resizing_factor = 1;
            }
            if(this.Circles[i].type==1){  // this is a helix part so bend it
               this.Circles[i].half_size(1);
               this.Circles[i].makecircle_Elipse(this.Circles[i].radius/3);
               resizing_factor = 1;
            }
            else if(this.Circles[i].type==2){
               this.Circles[i].half_size(1);
               //this.Circles[i].makecircle_Elipse(this.Circles[i].radius/4.0);
               if(this.Circles[i].last_four){
                    resizing_factor++;
                    this.Circles[i].half_size(-1*resizing_factor);
                    this.Circles[i].makecircle_Elipse(this.Circles[i].radius/3);
                }
                else{ 
                    resizing_factor = 1;
                    //this.Circles[i].makecircle_Elipse(this.Circles[i].radius/2.0);
                    this.Circles[i].half_size(resizing_factor);
                }               
            }
        }


}

backBoneStructure.prototype.create_Geometry = function(){ //creates the geometry stream of the backbone

    var vertexInfo = o3djs.primitives.createVertexInfo();
    var positionStream = vertexInfo.addStream(3, o3djs.base.o3d.Stream.POSITION);
    var normalStream   = vertexInfo.addStream(3, o3djs.base.o3d.Stream.NORMAL);
    var textCoordStream= vertexInfo.addStream(2, o3djs.base.o3d.Stream.TEXCOORD, 0);
    var total_elements = 0;
    for(var k=0; k<this.Circles.length; k++){
       // if((k+1)*this.Circles[0].points.length*3>=60000)
       //     break;
        var curr_circle = this.Circles[k];
        for(var m=0; m<curr_circle.points.length; m++){
            positionStream.addElement(curr_circle.points[m].Point[0], curr_circle.points[m].Point[1], curr_circle.points[m].Point[2]);
            var normal = subPoints(curr_circle.center, curr_circle.points[m].Point);
            normal = o3djs.math.normalize(normal);
            normalStream.addElement(normal[0], normal[1], normal[2]);
            textCoordStream.addElement(m/(curr_circle.points.length-1), k/(this.Circles.length-1));
        }
    }

      for(k=0; k<this.Circles.length-1; k++){
        //  if((k+1)*this.Circles[0].points.length*3>=60000)
         //   break;
        curr_circle = this.Circles[k];
        for(m=0; m<curr_circle.points.length; m++){

            vertexInfo.addTriangle((k+1)*curr_circle.points.length+m,
                                   k*curr_circle.points.length + m,
                                   (m!=curr_circle.points.length-1) ? k*curr_circle.points.length + m + 1 : k*curr_circle.points.length);

            vertexInfo.addTriangle((m!=curr_circle.points.length-1) ? k*curr_circle.points.length + m + 1 : k*curr_circle.points.length,
                                   (m!=curr_circle.points.length-1) ? (k+1)*curr_circle.points.length + m +1 : (k+1)*curr_circle.points.length,
                                   (k+1)*curr_circle.points.length + m);
        }
      }
    return vertexInfo;
}

backBoneStructure.prototype.reorder_points = function(first_circle, second_circle){

 
   var normals_degree =  getDegree(first_circle.normal, second_circle.normal);

   var first_circle_ray = subPoints(first_circle.points[0].Point, first_circle.center);
   var closest = null;
   var optimum_degree = normals_degree < 90 ? 10000 : 0 ;
   var opt_index = 0;
   for(var i=0; i<second_circle.points.length; i++){

        var ray = subPoints(second_circle.points[i].Point, second_circle.center);
        var curr_degree = getDegree(first_circle_ray, ray);

        if(normals_degree<90 && curr_degree<optimum_degree){
            closest = second_circle.points[i];
            optimum_degree = curr_degree;
            opt_index = i;
        }
        else if(normals_degree>=90 && curr_degree>optimum_degree){
            closest = second_circle.points[i];
            optimum_degree = curr_degree;
            opt_index = i;
        }
    }
    var new_order = [];
    new_order.push(closest);

    for(var i=opt_index+1; i!=opt_index; i++){

        if(i==second_circle.points.length){
            i = -1;
            continue;
        }
        new_order.push(second_circle.points[i]);
    }
    second_circle.points = new_order;
}

backBoneStructure.prototype.create3D_Object = function(first_circle, second_circle){

    var vertexInfo = o3djs.primitives.createVertexInfo();
    var positionStream = vertexInfo.addStream(3, o3djs.base.o3d.Stream.POSITION);
    var normalStream   = vertexInfo.addStream(3, o3djs.base.o3d.Stream.NORMAL);
    var textCoordStream= vertexInfo.addStream(2, o3djs.base.o3d.Stream.TEXCOORD, 0);
    var total_elements = 0;

    for(var m=0; m<first_circle.points.length; m++){
        positionStream.addElement(first_circle.points[m].Point[0], first_circle.points[m].Point[1], first_circle.points[m].Point[2]);
        var normal = subPoints(first_circle.points[m].Point, first_circle.center);
        normal = o3djs.math.normalize(normal);
        normalStream.addElement(normal[0], normal[1], normal[2]);
        textCoordStream.addElement(m/(first_circle.points.length-1), 0);
    }

    for(var m=0; m<second_circle.points.length; m++){
        positionStream.addElement(second_circle.points[m].Point[0], second_circle.points[m].Point[1], second_circle.points[m].Point[2]);
        var normal = subPoints(second_circle.points[m].Point, second_circle.center);
        normal = o3djs.math.normalize(normal);
        normalStream.addElement(normal[0], normal[1], normal[2]);
        textCoordStream.addElement(m/(second_circle.points.length-1), 1);
    }


     

    for(m=0; m<first_circle.points.length; m++){
        vertexInfo.addTriangle(first_circle.points.length+m,
                               m,
                               (m!=first_circle.points.length-1) ? m + 1 : 0);
        vertexInfo.addTriangle(first_circle.points.length + m,
                               (m!=first_circle.points.length-1) ? m + 1 : 0,
                               (m!=first_circle.points.length-1) ? first_circle.points.length + m + 1 : first_circle.points.length);
        
    }
    return vertexInfo;
}

backBoneStructure.prototype.Secondry_Geometry = function(){

    var temp;
    var old_type = -10;
    for(var i=0; i<this.Circles.length-1; i++){
      if(this.Circles[i].type!=old_type){
        
        var vertex_Info = this.create3D_Object(this.Circles[i], this.Circles[i+1]);
        this.curves_vertex_Infos.push([vertex_Info]);
        this.curves_types.push(this.Circles[i].type);
        old_type = this.Circles[i].type;
        continue;
      }

      var vertex_Info = this.create3D_Object(this.Circles[i], this.Circles[i+1]);
      this.curves_vertex_Infos[ this.curves_vertex_Infos.length-1].push(vertex_Info);

      //this.vertex_Infos.push(vertex_Info);
    }
}


function Cylinder(resolution, radius){

    this.mainPoints = []; // it is a list of points [p1],[p2],[p3],[p4]
    this.startPoint = []; //first end point of the cylinder
    this.endPoint   = []; //end point of the cylinder
    this.res        = resolution ; //how many times it will be divided in to 2
    this.radius     = radius;
    this.circleRes  = 10;
  

    this.Circles    = []; //holds one circle for each of the main point

    this.setStartPnt = function(pnt){this.startPoint = pnt;}
    this.setEndPnt = function(pnt) {this.endPoint = pnt;}
    this.calc_MainPoints = function(){

        if(this.res<=this.mainPoints.length){
            var newPoint = addPoints(this.mainPoints[this.mainPoints.length-1],  subPoints(this.mainPoints[this.mainPoints.length-1], this.mainPoints[this.mainPoints.length-2])); //this is to add one more point to find
            this.mainPoints.push(newPoint);
            return;
        }
        else if(this.mainPoints.length==0){
            this.mainPoints.push(this.startPoint);
            newPoint = addPoints(this.startPoint, this.endPoint);
            newPoint = divPoint(newPoint,2.0);
            this.mainPoints.push(newPoint);
            this.mainPoints.push(this.endPoint);
            this.calc_MainPoints();
        }
        else if(this.mainPoints.length>2){
            var newList = [];
            for(var i=0; i<this.mainPoints.length-1; i++){

                newList.push(this.mainPoints[i]);
                newPoint = addPoints(this.mainPoints[i], this.mainPoints[i+1]);
                newPoint = divPoint(newPoint, 2.0);
                newList.push(newPoint);
            }
            newList.push(this.endPoint);
            delete this.mainPoints;
            this.mainPoints = newList;
            this.calc_MainPoints();
        }
        return;
    }

    this.calc_Circles = function(){//resalution of the circles

        for(var i=0; i<this.mainPoints.length-1; i++){
            var newCircle = new Circle();
            newCircle.ID = i;
            newCircle.center = this.mainPoints[i];
            newCircle.nextPoint = this.mainPoints[i+1];
            newCircle.radius    = this.radius;
            newCircle.calc_CirclePoints(this.circleRes, 0);
            this.Circles.push(newCircle);
        }
    }


    this.create_Cylinder = function(){

        var vertexInfo = o3djs.primitives.createVertexInfo();
        var positionStream = vertexInfo.addStream(3, o3djs.base.o3d.Stream.POSITION);
        var normalStream   = vertexInfo.addStream(3, o3djs.base.o3d.Stream.NORMAL);
        var textCoordStream= vertexInfo.addStream(2, o3djs.base.o3d.Stream.TEXCOORD, 0);

        for(var k=0; k<this.Circles.length; k++){
            var curr_circle = this.Circles[k];
            for(var m=0; m<curr_circle.points.length; m++){
                positionStream.addElement(curr_circle.points[m].Point[0], curr_circle.points[m].Point[1], curr_circle.points[m].Point[2]);
                var normal = subPoints(curr_circle.points[m].Point, curr_circle.center);
                normal = o3djs.math.normalize(normal);
                normalStream.addElement(normal[0], normal[1], normal[2]);
                textCoordStream.addElement(m/(curr_circle.points.length-1), k/(this.Circles.length-1));
            }
        }

          for(k=0; k<this.Circles.length-1; k++){
            curr_circle = this.Circles[k];
            for(m=0; m<curr_circle.points.length; m++){

                vertexInfo.addTriangle((k+1)*curr_circle.points.length+m,
                                       k*curr_circle.points.length + m,
                                       (m!=curr_circle.points.length-1) ? k*curr_circle.points.length + m + 1 : k*curr_circle.points.length);
                vertexInfo.addTriangle((k+1)*curr_circle.points.length + m,
                                       (m!=curr_circle.points.length-1) ? k*curr_circle.points.length + m + 1 : k*curr_circle.points.length,
                                       (m!=curr_circle.points.length-1) ? (k+1)*curr_circle.points.length + m +1 : (k+1)*curr_circle.points.length);
            }
          }
        return vertexInfo;
    }
}

function atomFromPDB(){
	this.window_loc = "";
	this.atomType = ""; //ATOM or HETATM
         this.secondry_type = 0 ; //unknown=0, a-helix = 1, b-sheet=2.
	this.ID		  = -1;
	this.atomName = ""; //
	this.resedueName = ""; //
	this.chainName   = ""; //chain name
	this.chainNumber   = 0;
	this.resedueNumber = "";
	this.coord = [];
	this.occupancy = -1; //occupancy of the atom
	this.temp_factor = -1; //temperature factor
	this.element = "";
	this.neighbours = []; //holds all the atoms that are close to this atom considering a threshold.(if d<2.0)
        this.tangent_vec = [];
        this.secondry_type = 0; //0=spagetti, 1=alpha heliz, 2=beta sheet
	this.getResedueName = function(){
		return this.resedueName;
	}
	this.setResedueName = function(name){
		this.resedueName = name;
	}
	this.getResedueID = function(){
		return this.resedueNumber;
	}
	this.getCoord = function(){
		return [Number(this.coord[0]), Number(this.coord[1]), Number(this.coord[2])];
	}
	this.getElement = function(){
		return this.element;
	}
	this.getAtomName = function(){
		return this.atomName;
	}
	this.getAtomID = function(){
		return this.ID;
	}
}

function protien(){
	this.name = "";
	this.ID   = "";
	this.atomsIDs = [];

	this.addAtomID = function(newAtomID){
        this.atomsIDs.push(newAtomID);
	}
}


function HELIX(){

    this.ID = 0;
    this.ser_Number = 0;
    this.helix_ID   = 0;
    this.chain_ID   = 0;
    this.init_Seq   = 0;
    this.end_Seq    = 0;
}

function SHEET(){

    this.ID = 0;
    this.strand_Num = 0;
    this.chain_ID   = 0;
    this.sheet_ID = 0;
    this.init_Seq = 0;
    this.end_Seq  = 0;
}


function PDBFileInput(filepath){
	this.ID   = -1;
	this.path = filepath;
        this.filename = "";
        this.surface_exist = false;
	this.data = "";
        this.atoms = [];
	this.hetAtoms = [];
	this.backBoneStructures = [];
	this.proteins = [];
	this.max_pnt  = [-1000,-1000,-1000];
	this.min_pnt  = [10000,10000,10000];
	this.octree_root = null;
        this.bonds_num = 0;
        this.transforms_atom = [];
        this.transforms_atom_sticks = [];
        this.transforms_sticks = [];
        this.stick_sphere_transforms = [];
        this.backbone_trans_chain_trace = [];         //holds the transforms of cylinders as [[...],[...],[...],[...],[...],[...]]
        this.backbone_trans_atoms_chain_trace = [];   //holds the transforms of spheres as [[...],[...],[...],[...],[...],[...]]
        this.backbone_trans_minimal = []  //holds the transforms of cylinders as [[...],[...],[...],[...],[...],[...]]
        this.backbone_trans_minimal_atoms = []  //holds the transforms of spheres as [[...],[...],[...],[...],[...],[...]]

        this.backbone_trans_full_atoms = []  //only holds the spheres which are not in minimal [[...],[...],[...],[...],[...],[...]]
        this.backbone_trans_full = []  //holds the transforms of cylinders which are not in minimal backbone [[...],[...],[...],[...],[...],[...]]
        this.wireframe_bonds_transform = null;
        this.protein_render_mode = 0;  //atoms=0, Balls and Sticks=1, Sticks=2, CA-CA Structure=3, minimal backbone=4, full back_bone=5
                                       //ribbons=6, surface=7
        this.sphere_0 = false;
        this.sphere_1 = false;
        this.sphere_2 = false;
        this.sphere_3 = false;
        this.sphere_4 = false;
        this.sphere_5 = false;

        this.cylinder_2 = false;
        this.cylinder_3 = false;
        this.cylinder_4 = false;
        this.cylinder_5 = false;
        
        this.all_cylinders = [];
        this.all_spheres   = [];
        this.helixes = [];
        this.sheets  = [];

        var b_spheres_created    = false;
        var b_minimal_cylinders  = false;
        var b_maximum_cylinders  = false;
        var b_back_bone_cylinders= false;
        var b_all_remaining_cylinders = false;


        this.lightColor = [];
        this.lightWorldPos = [];
        this.lightViewProjection = [];
        this.shadowMap = null;
	this.shadow_material = null;

        this.alpha_Carbon_IDs = [];
        this.beta_sheets      = []; // it holds the data as [[1,2,3,4,5,6],[30,31,32,33,34, 35],[ 111, 112, 113, 114, 115], [159,160, 161, 162, 163]]
        this.alpha_helixes    = []; //it halds the data as beta_sheets.
        this.curve_directions = [];
        this.curve_normals= [];

        this.ribbons_points = [];
	//alert("Construn Func. Exit");
}



PDBFileInput.prototype.calculate_BackBoneNormals = function(){

   var newDirection = [0.0, 1.0, 1.0];
   var newNormal    = [0.0, 1.0, 0.0];
   this.curve_directions.push(newDirection);
   this.curve_normals.push(newNormal);

  for(var i=1; i<this.alpha_Carbon_IDs.length-1; i++){

      var direction = subPoints( this.atoms[this.alpha_Carbon_IDs[i+1]].getCoord(), this.atoms[this.alpha_Carbon_IDs[i-1]].getCoord() );
      direction = normalizeVec3D(direction);
      this.curve_directions.push(direction);
      this.atoms[this.alpha_Carbon_IDs[i]].tangent_vec = direction;

      var vector_1 = subPoints( this.atoms[this.alpha_Carbon_IDs[i-1]].getCoord(), this.atoms[this.alpha_Carbon_IDs[i]].getCoord() );
      var vector_2 = subPoints( this.atoms[this.alpha_Carbon_IDs[i+1]].getCoord(), this.atoms[this.alpha_Carbon_IDs[i]].getCoord() );

      var cross = crossProduct(vector_1, vector_2);
      cross = crossProduct(cross, direction);
      cross = normalizeVec3D(cross);
      this.curve_normals.push(cross);
  }
  i = this.alpha_Carbon_IDs.length-1;
  this.curve_directions.push([0.0, 1.0, 1.0]);
  this.curve_normals.push([0.0, 1.0, 0.0]);

  /*for(var i=0; i<this.beta_sheets.length; i++){
     for(var j=0; j<this.beta_sheets[j].lenght; i++){

     }
  }
  */

  var alpha_helix_1 = [16, 17, 18, 19, 20, 21, 22,23,24, 25];
  var alpha_helix_2 = [65, 66,67,68,69,70,71,72,73,74];
  var alpha_helix_3 = [87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103];
  var alpha_helix_4 = [127,128,129,130,131,132,133,134,135,136,137];
  var alpha_helix_5 = [152,153,154,155,156,157,158,159,160,161,162,163,164];
  this.alpha_helixes.push(alpha_helix_1);
  this.alpha_helixes.push(alpha_helix_2);
  this.alpha_helixes.push(alpha_helix_3);
  this.alpha_helixes.push(alpha_helix_4);
  this.alpha_helixes.push(alpha_helix_5);

  for(i=0; i<this.alpha_helixes.length; i++){
     var start_ID  = this.alpha_Carbon_IDs[this.alpha_helixes[i][0]];
     var start_pnt  = this.atoms[start_ID].getCoord();
     var newPoints = [];
     newPoints.push(start_pnt);
    for(var j=0; j<this.alpha_helixes[i].length-1; j++){

        var first_ID  = this.alpha_Carbon_IDs[this.alpha_helixes[i][j]];
        var second_ID = this.alpha_Carbon_IDs[this.alpha_helixes[i][j+1]];
        var curr_curve = [];
        var first_pnt  = this.atoms[first_ID].getCoord();
        var second_pnt = this.atoms[second_ID].getCoord();
        var first_tangent=this.atoms[first_ID].tangent_vec;
        var second_tangent = this.atoms[second_ID].tangent_vec;


        var points = [];
        points.push(first_pnt);
        points.push(second_pnt);
        points.push(first_tangent);
        points.push(second_tangent);
        for(var t=0.1; t<1.0;){

            var newPnt = findPoint_Hermite(points, t);
            newPoints.push(newPnt);
            t += 0.1;
        }
    }

    var end_ID  = this.alpha_Carbon_IDs[this.alpha_helixes[i][this.alpha_helixes[i].length-1]];
    var end_pnt  = this.atoms[start_ID].getCoord();
    newPoints.push(end_pnt);
    this.ribbons_points.push(newPoints);
  }

}



PDBFileInput.prototype.setID = function(id){
	this.ID = id;
}

PDBFileInput.prototype.getID = function(){
	return this.ID;
}

PDBFileInput.prototype.getPath = function() {
	return this.path;
}

PDBFileInput.prototype.setPath = function(filePath){
	this.path = filePath;
}


PDBFileInput.prototype.IsNumeric = function(sText)
{
   var ValidChars = "0123456789.-";
   for (var i = 0; i < sText.length; i++)
   {
      if (ValidChars.indexOf(sText.charAt(i)) < 0)
		 return false;
   }
   return true;
}


PDBFileInput.prototype.getString = function(sText, index1, index2){
    var ValidChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghjklmnopqrstuwxyz0123456789";
    var result_string="";
    for(var i=index1; i<=index2; i++){

        if(ValidChars.indexOf(sText.charAt(i)) >= 0)
            result_string = result_string + sText.charAt(i);
    }
    return result_string;
}

PDBFileInput.prototype.getNumberFloat = function(sText, index1, index2){
    var ValidChars = "0123456789.-";
    var result_string="";
    for(var i=index1; i<=index2; i++){

        if(ValidChars.indexOf(sText.charAt(i)) >= 0)
            result_string = result_string + sText.charAt(i);
    }

    return Number(result_string);
}

PDBFileInput.prototype.getNumberInt = function(sText, index1, index2){
    var ValidChars = "0123456789.-";
    var result_string="";
    for(var i=index1; i<=index2; i++){

        if(ValidChars.indexOf(sText.charAt(i)) >= 0)
            result_string = result_string + sText.charAt(i);
    }

    return Number(result_string);
}

PDBFileInput.prototype.IsString = function(sText)
{
   var ValidChars = "0123456789.-";
   for (var i = 0; i < sText.length; i++)
   {
      if (ValidChars.indexOf(sText.charAt(i)) < 0)
		 return false;
   }
   return true;
}

function createXMLHttpRequest() {
   try { return new XMLHttpRequest(); } catch(e) {}
   try { return new ActiveXObject("Msxml2.XMLHTTP"); } catch (e) {}
   alert("XMLHttpRequest not supported");
   return null;
}

var xhttp_request = null;
var xmlDoc = null;
function read_PathWAy_XML(filepath){
   xhttp_request = null;
  var window_loc = giveWindowLoc();
   if (!xhttp_request) {
    xhttp_request = CreateHTTPRequestObject ();   // defined in xml.js
  }
  if (xhttp_request) {
        // The requested file must be in the same domain that the page is served from.
     xhttp_request.open ("GET", filepath, false);    // async
     xhttp_request.setRequestHeader('User-Agent',navigator.userAgent);
     xhttp_request.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
     //xhttp_request.onreadystatechange = readXML;
     xhttp_request.send(null);
	//alert("Exit  readFile()");
      xmlDoc = xhttp_request.responseXML;
      parse_XML_File(xmlDoc);
  }
}

function readXML(){
     if (xhttp_request.readyState == 4) {
    if (xhttp_request.status == 200 || xhttp_request.status == 304) {
         xmlDoc=xhttp_request.responseXML;
    }
    else {
        alert ("Operation failed.");
        return;
        }
    }
   if(xmlDoc.readyState =="complete"){
    //Using documentElement Properties
    //Output company
    alert("XML Root Tag Name: " + xmlDoc.documentElement.tagName);

    //Using firstChild Properties
    //Output year
    alert("First Child: " + xmlDoc.documentElement.childNodes[1].firstChild.tagName);

    //Using lastChild Properties
    //Output average
    alert("Last Child: " + xmlDoc.documentElement.childNodes[1].lastChild.tagName);

    //Using nodeValue and Attributes Properties
    //Here both the statement will return you the same result
    //Output 001
    alert("Node Value: " + xmlDoc.documentElement.childNodes[0].attributes[0].nodeValue);
    alert("Node Value: " + xmlDoc.documentElement.childNodes[0].attributes.getNamedItem("id").nodeValue);

    //Using getElementByTagName Properties
    //Here both the statement will return you the same result
    //Output 2000
    alert("getElementsByTagName: " + xmlDoc.getElementsByTagName("year")[0].attributes.getNamedItem("id").nodeValue);

    //Using text Properties
    //Output John
    alert("Text Content for Employee Tag: " + xmlDoc.documentElement.childNodes[0].text);

    //Using hasChildNodes Properties
    //Output True
    alert("Checking Child Nodes: " + xmlDoc.documentElement.childNodes[0].hasChildNodes);
  }
}


PDBFileInput.prototype.readFile = function(filename)
{
    //getsurface.php?somevar=somestring&anothervar=anotherstring&arr[0]=fgdkjl&arr[1]
	//alert("readFile() " + filename);
	var oRequest = (navigator.appName.indexOf('Microsoft')>=0)?new ActiveXObject('Microsoft.XMLHTTP'):new XMLHttpRequest();
	oRequest.open("GET",filename,true);
	oRequest.setRequestHeader('User-Agent',navigator.userAgent);

	oRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	//alert("setRequestHeader");
	oRequest.send(null);
	alert("Exit  readFile()");
	return  oRequest.responseText;
}

PDBFileInput.prototype.calculate_Surface = function(){

	var oRequest = createXMLHttpRequest();
	oRequest.open("GET",filename,true);
	oRequest.setRequestHeader('User-Agent',navigator.userAgent);

	oRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
	//alert("setRequestHeader");
	oRequest.send(null);
	alert("Exit  readFile()");
	return  oRequest.responseText;

}

function onClickLoadPDBFile(form){
    
    var strFile = form.loadPDBFile.value;
    var oRequest = createXMLHttpRequest();
}

function calculateSurface(filename){
    var oRequest = createXMLHttpRequest();
    oRequest.open("GET",'../SurfacePDB.php?filename='+filename ,true);
    oRequest.setRequestHeader('User-Agent',navigator.userAgent);

    oRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    //alert("setRequestHeader");
    oRequest.send(null);
    alert("Exit  readFile()");
    return  oRequest.responseText;
}

PDBFileInput.prototype.constructBackBone = function(){

	var allIDS = "";
	//alert("Inside Construct Backbone");
	//alert("Atoms Length= " + this.atoms.length);
	var new_backBone;
	var chain_num = -1;
    this.backBoneStructure = new backBoneStructure();
    var last_backbone_name = "";
	for(var i=0; i<this.atoms.length; i++){		
		if(last_backbone_name!=this.atoms[i].chainName){
                    last_backbone_name = this.atoms[i].chainName;
                    chain_num +=1;
                    this.atoms[i].chainNumber = chain_num;
                    new_backBone = new backBoneStructure();
                    this.backBoneStructures.push(new_backBone);
                }
		  this.atoms[i].chainNumber = chain_num;	
          if(this.atoms[i].atomName=="CA"){   //putting the atoms ID which constructs the backbone
			new_backBone.atomsID.push(this.atoms[i].ID);
		}
	}
	//alert("BackBone IDs read ");

       for(i=0; i<this.backBoneStructures.length; i++){
           this.backBoneStructures[i].pdb_data = this;
           this.backBoneStructures[i].constructCurvePoints(this.atoms, 20);
           this.backBoneStructures[i].Secondry_Geometry();
       }
       
       /*
       for(i=0; i<this.backBoneStructures.length; i++){
            var vertex_info = this.backBoneStructures[i].create_Geometry();
            this.spaghetti_cylinder_trans = [];
                this.spaghetti_cylinder_trans = [];
                this.spaghetti_cylinders = [];
           this.backBoneStructures[i].spaghetti_cylinders.push(vertex_info);
       }
       */
	alert("Return from constructBone");
}


//checks in a list which defines the type of the bonds,
//for instance c-c, c-o, n-c bonds...
function check_Bond_Exists(list, first_char){

    for(var i=0; i<list.length; i++){
        if(list[i].start_atom_name==first_char)
            return true;
    }
    return false;
}

function getColor(element_name){
    if(element_name=="O")
        return redAtom;
    else if(element_name=="C")
        return whiteAtom;
    else if(element_name=="S")
        return yellowAtom;
    else if(element_name=="H")
        return greenAtom;
    else if(element_name=="N")
        return blueAtom;
    return null;
}

function getShape(element_name){
    for(var i=0; i<existing_bonds.length; i++){
        if(element_name==existing_bonds[i].start_atom_name)
        return existing_bonds[i];
    }
    return null;
}

//Calculates the the neighbours of an atom using octree, complexity of it is O(m*n)
//m is the number of atoms in an octant and in the neighbour octants which are crossing
//with the atom's volume.
PDBFileInput.prototype.FindBonds = function() {

    for(var i=0; i<this.atoms.length; i++){

       var x = Number(this.atoms[i].coord[0]);
       var y = Number(this.atoms[i].coord[1]);
       var z = Number(this.atoms[i].coord[2]);

       crossingOctants = [];
       findCrossingOctants(2.0, x, y, z,  this.octree_root);
       if(crossingOctants.length>0){

           for(var j=0; j<crossingOctants.length; j++){
               for(var k=0; k<crossingOctants[j].atom_IDS.length; k++){

                   if(Number(crossingOctants[j].atom_IDS[k])!=Number(this.atoms[i].getAtomID())){

                       var atom_id = Number(crossingOctants[j].atom_IDS[k]);
                       var coord_first = [];
                       var coord_second = [];
                       coord_first[0] = Number(this.atoms[i].getCoord()[0]);
                       coord_first[1] = Number(this.atoms[i].getCoord()[1]);
                       coord_first[2] = Number(this.atoms[i].getCoord()[2]);
                       if(atom_id==0 || atom_id>=this.atoms.length)
                           continue;
                       coord_second[0] = Number(this.atoms[atom_id-1].getCoord()[0]);
                       coord_second[1] = Number(this.atoms[atom_id-1].getCoord()[1]);
                       coord_second[2] = Number(this.atoms[atom_id-1].getCoord()[2]);
                       var dist = Math.sqrt(Math.pow(coord_first[0] - coord_second[0], 2) + Math.pow(coord_first[1] - coord_second[1], 2) +Math.pow(coord_first[2] - coord_second[2], 2));

                       if(dist<=2.0){
                         var first_char = this.atoms[i].element;
                         var second_char= this.atoms[atom_id-1].element;
                         var exist = check_Bond_Exists(existing_bonds ,first_char);
                         if(exist==false){
                             var material = getColor(first_char);
                             var new_shape = o3djs.primitives.createCylinder( g_pack, material, 0.2, 1,  20, 20);   // Number of vertical subdivisions.
                             var new_bond = new stick();
                             new_bond.start_atom_name = first_char;
                             new_bond.end_atom_name = second_char;
                             new_bond.material    =  getColor(first_char);
                             new_bond.shape = new_shape;
                             existing_bonds.push(new_bond);
                         }

                         this.atoms[i].neighbours.push(Number(crossingOctants[j].atom_IDS[k]));
                         this.bonds_num++;
                       }
                   }
               }
           }
       }
    }
}


//This function calculates the neighbours of each atom by checking all the atoms. complexity of it is O(n^2)
PDBFileInput.prototype.check_Bonds = function(){


    for(var i=0; i<this.atoms.length; i++){
        for(var j=0; j<this.atoms.length; j++){

           if(i==j)
               continue;
           var coord_first = [];
           var coord_second = [];
           coord_first[0] = Number(this.atoms[i].getCoord()[0]);
           coord_first[1] = Number(this.atoms[i].getCoord()[1]);
           coord_first[2] = Number(this.atoms[i].getCoord()[2]);
           coord_second[0] = Number(this.atoms[j].getCoord()[0]);
           coord_second[1] = Number(this.atoms[j].getCoord()[1]);
           coord_second[2] = Number(this.atoms[j].getCoord()[2]);
           var dist = Math.sqrt(Math.pow(coord_first[0] - coord_second[0], 2) + Math.pow(coord_first[1] - coord_second[1], 2) +Math.pow(coord_first[2] - coord_second[2], 2));
           if(dist<=2.0){
               this.atoms[i].neighbours.push(Number(this.atoms[j].ID));
               this.bonds_num++;
            }
        }

    }

}


PDBFileInput.prototype.readFileMain = function(newID, window_loc){

  var text = this.readFile(this.path);
  //var redMat_path = window_loc + 'red_Mat.txt';
  //alert("redMath_path  " + redMat_path);
  //var redMat = this.readFile(redMat_path);
  //alert(this.path);
  //alert("Text Size= " + text.length);

  this.path.replace('')

  //alert("Before reading file");

  var fname= this.path.replace(/^.*\//, '');
  var word = this.path.split("/");
  fname = word.pop();

  //alert("After reading the file");

  this.filename = fname;

  //var stringList = text.replace(/\n/g, " ").split(" ");

  var stringList  = text.split("\n");

  var allWords = [];
  var k=0;
  var rere = 0;

  if(stringList.lenght<=0)
	return false;
  var last_pro_ID = 0;
  var m=0;
  var last_chain_Name = "";
  for(var i=0; i<stringList.length; i++){
      var first_element = this.getString(stringList[i], 0, 5);
      var newAtom;
      if(first_element=="ATOM"  || first_element=="HETATM"){
          newAtom = new atomFromPDB();
          newAtom.atomType = first_element;
          newAtom.ID 		 = this.getNumberInt(stringList[i], 6, 10);
          newAtom.atomName       = this.getString(stringList[i], 12, 15);
          if((newAtom.atomName=="CA" || newAtom.atomName=="cA" || newAtom.atomName=="Ca" || newAtom.atomName=="ca") && allWords[i]=="ATOM")
                this.alpha_Carbon_IDs.push(this.atoms.length);
          
          newAtom.resedueName = this.getString(stringList[i], 17, 19);
          newAtom.chainName   = this.getString(stringList[i], 21, 21);
          if(last_chain_Name!=newAtom.chainName){
              last_chain_Name = newAtom.chainName;
              last_pro_ID = 0;
          }

          newAtom.resedueNumber = this.getNumberInt(stringList[i], 22, 25);
          //if(Math.abs(newAtom.resedueNumber - last_pro_ID)>1)
          //    newAtom.resedueNumber = last_pro_ID+1;
          var x = this.getNumberFloat(stringList[i], 30, 37);
          var y = this.getNumberFloat(stringList[i], 38, 45);
          var z = this.getNumberFloat(stringList[i], 46, 53);
          newAtom.coord = [x, y, z];
          if(m==0){
               this.max_pnt[0] = x;
               this.max_pnt[1] = y;
               this.max_pnt[2] = z;
               this.min_pnt[0] = x;
               this.min_pnt[1] = y;
               this.min_pnt[2] = z;
               m++;
          }
          else{
                if(this.max_pnt[0]<x)
                   this.max_pnt[0] = x;
                if(this.max_pnt[1]<y)
                   this.max_pnt[1] = y;
                if(this.max_pnt[2]<z)
                   this.max_pnt[2] = z;

                if(this.min_pnt[0]>x)
                   this.min_pnt[0] = x;
                if(this.min_pnt[1]>y)
                   this.min_pnt[1] = y;
                if(this.min_pnt[2]>z)
                   this.min_pnt[2] = z;
	  }
          newAtom.occupancy = this.getNumberFloat(stringList[i], 54, 59);
          newAtom.temp_factor = this.getNumberFloat(stringList[i], 60, 65);
	  newAtom.element     = this.getString(stringList[i], 76, 77);
	  k++;
      }
      else if(first_element=="HELIX" || first_element=="SHEET"){
            if(first_element=="HELIX"){
                var new_helix = new HELIX();
                new_helix.ser_Number = this.getNumberInt(stringList[i], 7, 9);
                new_helix.helixID    = this.getString(stringList[i], 11, 13);
                new_helix.init_Seq   = this.getNumberInt(stringList[i], 21, 24);
                new_helix.chain_ID   = this.getString(stringList[i], 31, 31);
                new_helix.end_Seq    = this.getNumberInt(stringList[i], 33, 36);
                this.helixes.push(new_helix);
            }
            else if(first_element == "SHEET"){
                var new_sheet = new SHEET();
                new_sheet.ID  = this.getNumberInt(stringList[i], 11, 13);
                new_sheet.strand_Num = this.getNumberInt(stringList[i], 7, 9);
                new_sheet.init_Seq   = this.getNumberInt(stringList[i], 22, 25);
                new_sheet.end_Seq    = this.getNumberInt(stringList[i], 33, 36);
                new_sheet.chain_ID   = this.getString(stringList[i], 32, 32);
                this.sheets.push(new_sheet);
            }
            continue;
      }
      else
	 continue;
     if(newAtom.atomType=="ATOM"){

            this.atoms.push(newAtom);
            newAtom.ID = this.atoms.length;
            if(last_pro_ID==newAtom.resedueNumber){
                    if(this.proteins.length==0)
                            continue;
                    this.proteins[this.proteins.length-1].atomsIDs.push(newAtom.ID);
            }
            else{
                    var newProtein  = new protien();
                    newProtein.name = newAtom.getResedueName();
                    newProtein.ID   = this.proteins.length; //  newAtom.getResedueID();
                    newProtein.atomsIDs.push(newAtom.getAtomID());
                    last_pro_ID = last_pro_ID+1;
                    this.proteins.push(newProtein);
            }
	}
       else if(newAtom.atomType == "HETATM"){
            newAtom.ID = this.hetAtoms.length;
            this.hetAtoms.push(newAtom);
       }

  }

  for(var i=0; i<this.atoms.length; i++){
      this.atoms[i].coord = [this.atoms[i].coord[0] - this.min_pnt[0], this.atoms[i].coord[1] - this.min_pnt[1], this.atoms[i].coord[2] - this.min_pnt[2]];
  }
  for(var i=0; i<this.hetAtoms.length; i++){
      this.hetAtoms[i].coord = [this.hetAtoms[i].coord[0] - this.min_pnt[0], this.hetAtoms[i].coord[1] - this.min_pnt[1], this.hetAtoms[i].coord[2] - this.min_pnt[2]];
  }
  this.max_pnt = [this.max_pnt[0] - this.min_pnt[0], this.max_pnt[1] - this.min_pnt[1], this.max_pnt[2] - this.min_pnt[2]];
  this.min_pnt = [0.0,0.0,0.0];

  for(var i=0; i<this.atoms.length; i++){
      if(this.atoms[i].atomName=="CA"){
           var type_set = false;
           for(var k=0 ;k<this.helixes.length; k++){
               if(this.atoms[i].resedueNumber>=this.helixes[k].init_Seq &&
                  this.atoms[i].resedueNumber<=this.helixes[k].end_Seq){
                     this.atoms[i].secondry_type = 1;
                     type_set = true;
                     break;
                  }
           }
           for(k=0; k<this.sheets.length && type_set==false; k++){
               if(this.atoms[i].resedueNumber>=this.sheets[k].init_Seq &&
                  this.atoms[i].resedueNumber<=this.sheets[k].end_Seq){
                       this.atoms[i].secondry_type = 2;
                       type_set = true;
                       break;
                  }
           }
           if(type_set==false)
               this.atoms[i].secondry_type = 0;
      }
  }

  var center = addPoints(this.max_pnt, this.min_pnt);
  center = divPoint(center, 2.0);
  var rad = subPoints(this.max_pnt, this.min_pnt);
  rad = divPoint(rad, 2.0);

  this.octree_root = new Octree(center, rad, 0);
  createTree(this.octree_root, 4);

  for(i=0; i<this.atoms.length; i++){
    putPoint(this.octree_root, this.atoms[i].coord[0], this.atoms[i].coord[1], this.atoms[i].coord[2], Number(this.atoms[i].getAtomID()));
  }
  mergeTree(0, 10);
  this.FindBonds();
  
  //this.check_Bonds();
  //var total_elements = 0;
  //for(i=0; i<allLeafs.length; i++){
  //    total_elements += allLeafs[i].atom_IDS.length;
  //}

 for(var i=0; i<allLeafs.length; i++)
     delete allLeafs[allLeafs.length-1-i];
 for(var i=0; i<crossingOctants.length; i++)
     delete crossingOctants[crossingOctants.length-1-i];

  
  this.constructBackBone();

//  this.calculate_BackBoneNormals();
  //this.backBoneStructure.shader_str = redMat;
  //alert("after construct backbone");

  this.ID = newID;
  return true;
}

function intersection_pnt(){

    this.point = [0,0,0];
}

var reslut_string;

PDBFileInput.prototype.pickingDraw = function(mode, rayNear, rayFar){

  var prev_inter_pnt = [1000, 1000, 1000];
  var intersection_points = [];
  var selected_atom = null;
  reslut_string = "_nothing selected_";
  // check for the intersection of the atoms according to the current view mode
   for(var i=0; i<this.atoms.length; i++){

        var interset_pnt = new intersection_pnt();
        var t = 0.0;
        var center = this.atoms[i].getCoord();
        var atom_name = this.atoms[i].element;
        var radius = 1.0;
        if(mode&1 && atom_name=="C")  radius = 1.7;
        else if(mode&1 && atom_name=="O") radius = 1.48;
        else if(mode&1 && atom_name=="N") radius = 1.65;
        else if(mode&1 && atom_name=="H") radius = 1.0;
        else if(mode&1 && atom_name=="S") radius = 1.50;
        else if(mode&2 ) radius = 0.3;
        else if(mode&4 )  radius = 0.2;

        if(mode&8 && this.atoms[i].atomName=="CA")
           radius = 0.2;
        else if(mode&8 && this.atoms[i].atomName!="CA")
           continue;

        if(mode&16 && (this.atoms[i].atomName=="CA" || this.atoms[i].atomName=="C" || this.atoms[i].atomName=="N"))
            radius = 0.2;
        else if(mode&16 && (this.atoms[i].atomName!="CA" || this.atoms[i].atomName!="C" || this.atoms[i].atomName!="N"))
            continue;

        if(mode&32 && (this.atoms[i].atomName=="CA" || this.atoms[i].atomName=="C" || this.atoms[i].atomName=="N" || this.atoms[i].atomName=="O"))
            radius = 0.2;
        else if(mode&32 && (this.atoms[i].atomName!="CA" || this.atoms[i].atomName!="C" || this.atoms[i].atomName!="N" || this.atoms[i].atomName!="O"))
            continue;


        var direction = [rayFar[0] - rayNear[0], rayFar[1] - rayNear[1], rayFar[2] - rayNear[2]];
        direction = normalizeVec3D(direction);
        var is_intersecting = IntersectRaySphere(rayNear, direction, center, radius, t, interset_pnt);

        if(is_intersecting>0){

             var distance_1 = distance(rayNear, prev_inter_pnt);
             var distance_2 = distance(rayNear, interset_pnt.point);

            if(distance_1>distance_2){
               prev_inter_pnt = interset_pnt.point;
               selected_atom = this.atoms[i];
               reslut_string = this.filename + " " + this.atoms[i].resedueName + " " + this.atoms[i].resedueNumber + "." + this.atoms[i].chainName;
               reslut_string = reslut_string + " " + this.atoms[i].atomName;
            }
            delete interset_pnt;
        }
   }

   //check for the intersection of the cylinders (bond_backbone) according to the current view mode
   var interset_pnt = new intersection_pnt();
   if(mode&2 || mode&4 || mode&8 || mode&16){
    for(i=0; i<this.atoms.length; i++){
       var curr_coord = this.atoms[i].getCoord();
       var radius = 0.2;
       for(var j=0; j<this.atoms[i].neighbours.length; j++){
           var neigh_ID =  Number(this.atoms[i].neighbours[j]);
           if(neigh_ID==this.atoms[i].ID)
               continue;
           var neighbour = this.atoms[neigh_ID-1];
           var neigh_coord = this.atoms[neigh_ID-1].getCoord();
           var sb = [rayFar[0] - rayNear[0], rayFar[1] - rayNear[1], rayFar[2] - rayNear[2]];
           var intersect = IntersectRayCylinder(rayNear, rayFar, curr_coord, neigh_coord, radius, interset_pnt); //replace intersection pnt with intersection object
           if(intersect){

               var distance_1 = distance(rayNear, prev_inter_pnt);
               var distance_2 = distance(rayNear, interset_pnt.point);

               var distance_a = distance(curr_coord, interset_pnt.point);
               var distance_b = distance(neigh_coord, interset_pnt.point);
               var betw = between(interset_pnt.point, rayNear, rayFar);
               if(distance_1>distance_2 && distance_a<distance_b && betw){
                 prev_inter_pnt = interset_pnt.point;
                 selected_atom  = this.atoms[i];
                 reslut_string  = this.filename + " " + this.atoms[i].resedueName + " " + this.atoms[i].resedueNumber + "." + this.atoms[i].chainName + " " + this.atoms[i].atomName;
                 reslut_string  = reslut_string + "--" + neighbour.resedueName + " " + neighbour.resedueNumber + "." + neighbour.chainName + " " + neighbour.atomName + " " + distance_2 + String.fromCharCode(143);

              }
           }
       }
     }
   }
  return selected_atom;

   //}


}