//This structure will hold all the data as, one HE_edge array, one HE_vertex array and one HE_face array. 
//The pointers will hold indexes to these arrays.
o3djs.require('o3djs.util');
o3djs.require('o3djs.math');
o3djs.require('o3djs.rendergraph');
o3djs.require('o3djs.primitives');
o3djs.require('o3djs.effect');
o3djs.require('o3djs.debug');
o3djs.require('o3djs.material');
o3djs.require('o3djs.pack');
o3djs.require('o3djs.scene');
o3djs.require('o3djs.fps');

var lightPos   = [];
var lightColor = [];

var c_atomTriangleShape = [];
var o_atomTriangleShape = [];
var n_atomTriangleShape = [];
var h_atomTriangleShape = [];



function crossProduct(vector1, vector2){

        if(vector1.length==2 && vector2.length==2){
            return vector1[0]*vector2[1] - vector1[1]*vector2[0];
        }
        else if(vector1.length==3 && vector2.length==3){
           return [crossProduct([vector1[1], vector1[2]],[vector2[1], vector2[2]]),
                  -crossProduct([vector1[0], vector1[2]],[vector2[0], vector2[2]]),
                   crossProduct([vector1[0], vector1[1]],[vector2[0], vector2[1]])];
        }
	return [0,0,0];
}

function dotProduct_3(vector1, vector2){
	
	var result = new Array(0.0,0.0,0.0);
	result[0] = vector1[0]*vector2[0];
	result[1] = vector1[1]*vector2[1];
	result[2] = vector1[2]*vector2[2];
	
	return result;	
}

function getLength(data){
        var length = 0;
        if(data.length==2)
            length = Math.sqrt(Math.pow(data[0],2) + Math.pow(data[1],2));
        else if(data.length==3)
           length = Math.sqrt(Math.pow(data[0],2) + Math.pow(data[1],2) + Math.pow(data[2],2));
	return length;
}

function normalize(data){

	var length;
        if(data.length==2)
            length = Math.sqrt(Math.pow(data[0],2) + Math.pow(data[1],2));
        else if(data.length==3)
           length = Math.sqrt(Math.pow(data[0],2) + Math.pow(data[1],2) + Math.pow(data[2],2));
	data[0] = data[0]/length;
	data[1] = data[1]/length;
        if(data.length>2)
            data[2] = data[2]/length;
	return data;
}

function product_vec(constant, data){
	
	data[0] = data[0]*constant;
	data[1] = data[1]*constant;
        if(data.length>2)
            data[2] = data[2]*constant;
	return data;
}

function subtuct_vector(vec1, vec2){

    if(vec1.length==2 && vec2.length==2){
        return [vec1[0]-vec2[0], vec1[1] - vec2[1]];
    }
    else if(vec1.length==3 && vec2.length==3){
        return [vec1[0]-vec2[0], vec1[1]-vec2[1], vec1[2]-vec2[2]];
    }
    return [];
}

function add_vector(vec1, vec2){

    if(vec1.length==2 && vec2.length==2){
        return [vec1[0]+vec2[0], vec1[1]+vec2[1]];
    }
    else if(vec1.length==3 && vec2.length==3){
        return [vec1[0]+vec2[0], vec1[1]+vec2[1], vec1[2]+vec2[2]];
    }
    return [];
}


function HE_edge(){
	this.vertex = null;
	this.next_vertex = null;
	this.pair_edge   = null;   //it is the other edge which is in the opposite direction
	this.face   = null;
	this.next   = null;  //it is the index of the vertex that the edge is directed to
	this.edge   = null;  //hold referance to the owner edge
	this.setDirection = false;  //sets if the direction of the half edge is setted
}

HE_edge.prototype.setEdge = function(source, dest) {
    this.vertex = source;
    this.next_vertex = dest;
}

HE_edge.prototype.setVertex = function(newvertex) {
    this.vertex = newvertex;
}
HE_edge.prototype.getVertex = function() {
    return this.vertex;
}
HE_edge.prototype.getOpposite = function() {
    return this.pair_edge;
}
HE_edge.prototype.setOpposite = function(newOpp) {
    this.pair_edge = newOpp;
}
HE_edge.prototype.getFace = function() {
    return this.face;
}
HE_edge.prototype.setFace = function(newface) {
    this.face = newface;
}
HE_edge.prototype.getNext = function() {
    return this.next;
}
HE_edge.prototype.setNext = function(new_hedge) {
    this.next = new_hedge;
}

function Edge(){
	this.vertex_IDs = [];
	this.vertices = [];
	this.faces  = [];
	this.HE_edges   = [];
	this.first_half_edge = null;
	this.second_half_edge = null;
	this.directionSet = false;	
}

Edge.prototype.getEdge = function(source, dest){

  if(this.HE_edges[0].getVertex()==source && this.HE_edges[0].next_vertex==dest)
      return this.HE_edges[0];
  else if(this.HE_edges[0].getVertex()==dest && this.HE_edges[0].next_vertex==source)
      return this.HE_edges[1];
  return null;
}

Edge.prototype.firstVertexID = function() {
    return this.vertex_IDs[0];
}
Edge.prototype.secondVertexID = function(){
    return this.vertex_IDs[1];
}
Edge.prototype.b_directionSet = function() {
    return this.directionSet;
}
Edge.prototype.getFace = function(index){
    return this.faces[index];
}

Edge.prototype.setFirst_HEdge = function(hedge){
    this.first_half_edge = hedge;
}
Edge.prototype.setSecond_HEdge = function(hedge) {
    this.second_half_edge = hedge;
}
Edge.prototype.setDirection = function(edge_1, edge_2, face){	 //edge_1 is already calculated, edge_2 will be calculated according to this edge

        var other_face = null;
        var half_edge_1 = null;
 
        if(this.faces[0]!=null && this.faces[0]!=face)
                other_face = this.faces[0];
        else if(this.faces[1]!=null && this.faces[1]!=face)
                other_face = this.faces[1];

        if(edge_1.HE_edges[0]!=null && edge_1.HE_edges[0].face==face)
                half_edge_1 = edge_1.HE_edges[0];
        else if(edge_1.HE_edges[1]!=null && edge_1.HE_edges[1].face==face)
                half_edge_1 = edge_1.HE_edges[1];

         if(edge_2.faces[0]==face)
               other_face = edge_2.faces[1];
         else
               other_face = edge_2.faces[0];

        if(edge_2.HE_edges[0].vertex == half_edge_1.vertex){

            edge_2.HE_edges[1].next = half_edge_1;
            edge_2.HE_edges[1].face = face;
            edge_2.HE_edges[0].face = other_face;
            edge_2.directionSet = true;
        }
        else if(edge_2.HE_edges[1].vertex == half_edge_1.vertex){

            edge_2.HE_edges[0].next = half_edge_1;
            edge_2.HE_edges[0].face = face;
            edge_2.HE_edges[1].face = other_face;
            edge_2.directionSet = true;
        }
        else if(edge_2.HE_edges[0].vertex==half_edge_1.next_vertex){
            half_edge_1.next = edge_2.HE_edges[0];
            edge_2.HE_edges[0].face = face;
            edge_2.HE_edges[1].face = other_face;
            edge_2.directionSet = true;
        }
        else if(edge_2.HE_edges[1].vertex==half_edge_1.next_vertex){
            half_edge_1.next = edge_2.HE_edges[1];
            edge_2.HE_edges[1].face = face;
            edge_2.HE_edges[0].face = other_face;
            edge_2.directionSet = true;
        }
        edge_1.directionSet = true;
        edge_2.directionSet = true;
        edge_2.HE_edges[0].setDirection = true;
        edge_2.HE_edges[1].setDirection = true;
}

function HE_vertex(){
	this.pos = [];   //x,y,z value of the vertex
	this.half_edges = []; //edges that are goint out from this vertex
	this.edges = []; //edges that this vertex is the one of the vertices of it.
	this.normal = []; //normal of the vertex
	this.faces  = []; //index of the corresponding faces with vertex

}

HE_vertex.prototype.calc_Normal = function(){
    
    var normal = [0,0,0];
    for(var i=0; i<this.faces.length; i++)
        normal = add_vector(normal, this.faces[i].normal);

    normal = [normal[0]/this.faces.length, normal[1]/this.faces.length, normal[2]/this.faces.length];
    normal = normalize(normal);
    this.normal = normal;
}

HE_vertex.prototype.getPoint = function(){
    return this.pos;
}
HE_vertex.prototype.setPosition = function(newPos) {
    this.pos = newPos;
}
HE_vertex.prototype.getEdges  = function() {
    return this.edges;
}
HE_vertex.prototype.addEdge  = function(edge)  {
    this.edges.push(edge);
}
HE_vertex.prototype.getFaces  = function() {
    return this.faces;
}
HE_vertex.prototype.addFace  = function(face) {
    this.faces.push(face);
}
HE_vertex.prototype.getNormal = function() {
    return this.normal;
}
HE_vertex.prototype.setNormal = function(newNorm) {
    this.normal = newNormal;
}

HE_vertex.prototype.calc_Normal_area = function(){

        var tot_area  = 0.0;
        var norm = [0.0, 0.0, 0.0];

        for(var i=0; i<this.faces.length; i++){
                tot_area += this.faces[i].area;
                var t =  product_vec(this.faces[i].area,this.faces[i].normal);
                norm[0] += t[0]*this.faces[i].area;
                norm[1] += t[1]*this.faces[i].area;
                norm[2] += t[2]*this.faces[i].area;
        }
        this.normal[0] = norm[0]/tot_area;
        this.normal[1] = norm[1]/tot_area;
        this.normal[2] = norm[2]/tot_area;
}


HE_vertex.prototype.getEdge = function(index1, index2){

    for(var i=0; i<this.edges.length; i++){

        if(this.edges[i].firstVertexID()==index1 && this.edges[i].secondVertexID()==index2){
            return this.edges[i];
        }
        else if(this.edges[i].firstVertexID()==index2 && this.edges[i].secondVertexID()==index1)
            return this.edges[i];
    }
    return null;
}


function HE_face(){	
	this.normal = []; //x,y,z values of the normal
	this.half_edges  = []; //half edges of the face
	this.edges  = []; //
	this.points = []; //this points
	this.area  =  0.0;
        this.color = [];  // r,g,b,a
	this.hedges_calculated = false;
        this.transform = null;
        this.shape     = null;
        this.shape_name= "";
        this.he_calculated = false;
}

HE_face.prototype.calc_Normal = function(){

  var first_half_edge;
  if(edges[0].HE_edges[0].face==this){
      first_half_edge = edges[0].HE_edges[0];
  }
  else if(edges[0].HE_edges[1].face == this){
      first_half_edge = edges[0].HE_edges[1];
  }
  else
      first_half_edge = null;

  if(first_half_edge==null){
      this.normal = [0,0,0];
      return;
  }

  var second_half_edge = first_half_edge.next;

  var point_1 = first_half_edge.vertex.pos;
  var point_2 = first_half_edge.next_vertex.pos;
  var point_3 = first_half_edge.next.next_vertex.pos;

  var vec1 = subtuct_vector(point1, point2);
  var vec2 = subtuct_vector(point3, point2);

  var normal = crossProduct(vec1, vec2);
  normal = normalize(normal);
  this.normal = normal;
}

HE_face.prototype.getHalfEdge = function(source, dest){

    for(var i=0; i<this.edges.length; i++){
        var half_ed = this.edges[i].getEdge(source, dest);
        if(half_ed!=null)
            return half_ed;

    }
    return null;
}


HE_face.prototype.getNormal = function() {
    return this.normal;
}
HE_face.prototype.setNormal = function(newNormal) {
    this.normal = newNormal;
}
HE_face.prototype.getEdges = function(){
    return edges;
}
HE_face.prototype.addEdge = function(edge) {
    this.edges.push(edge);
}

HE_face.prototype.getPoints = function() {
    return this.points;
}
HE_face.prototype.setPoint = function(newPnt) {
    this.points.push(newPnt);
}
HE_face.prototype.removePnt = function(index) {
        if(this.point.length > index)
                points.splice(index);
}

HE_face.prototype.getPoint = function(index){
        if(this.points.length>index)
                return this.points[index];
         return -1;
}

HE_face.prototype.getEdge = function(index){
        if(this.half_edges.length>index)
                return this.half_edges[index];
        return -1;
}

HE_face.prototype.edgesNotCalc = function(){

        if(this.edges[0].b_directionSet() || this.edges[1].b_directionSet() || this.edges[2].b_directionSet())
                return true;
        return false;
}

HE_face.prototype.getEdge = function(index){
    return this.edges[index];
}	          //returns an edge
HE_face.prototype.getFirst_HEdge = function(index) {
    return this.half_edges[index];
} //returns a half_edge

HE_face.prototype.calc_Normal = function(){
        var h_edge_1;
        if(this.edges[0].HE_edges[0].face==this)
            h_edge_1 = this.edges[0].HE_edges[0];
        else
            h_edge_1 = this.edges[0].HE_edges[0].getOpposite();
        var h_edge_2 = h_edge_1.next;

        var vector_1 = [h_edge_1.vertex.pos[0] - h_edge_1.next_vertex.pos[0], h_edge_1.vertex.pos[1] - h_edge_1.next_vertex.pos[1], h_edge_1.vertex.pos[2] - h_edge_1.next_vertex.pos[2]];
        var vector_2 = [h_edge_2.next_vertex.pos[0] - h_edge_2.vertex.pos[0], h_edge_2.next_vertex.pos[1] - h_edge_2.vertex.pos[1], h_edge_2.next_vertex.pos[2] - h_edge_2.vertex.pos[2]];
        this.normal = crossProduct(vector_1, vector_2);
}

HE_face.prototype.calc_Area = function(){

         var h_edge_1 = this.half_edges[0];
         var h_edge_2 = h_edge_1.next;

         var vector_1 = [h_edge_1.vertex[0] - h_edge_1.next_vertex[0], h_edge_1.vertex[1] - h_edge_1next_vertex[1], h_edge_1.vertex[2] - h_edge_1.next_vertex[2]];
         var vector_2 = [h_edge_2.next_vertex[0] - h_edge_2.vertex[0], h_edge_2.next_vertex[1] - h_edge_2.vertex[1], h_edge_2.next_vertex[2] - h_edge_2.vertex[2]];
         var result = crossProduct(vector_1, vector_2);
         this.area = getLength(result)/2.0;
}


function Half_Edge_Data(){	
	this.vertexArray = [];  //Half edge vertex arrays
	this.half_edgeArray = [];    //Half edge struxtures' edge array
	this.faceArray = [];    //Half edge structures' face array
        this.edgeArray = [];    //holds all the edges in the backbone
	
	this.indexArray = [];
	this.posArray   = [];
        this.transforms = [];  //holds the mesh of the triangle transform
        this.color      = [];
        this.pos        = [];
        
        this.shadowMap  = null;
        this.specular   = [];
        this.shininess  = 0;
        this.lightColor = [];
        this.lightWorldPos = [];
        this.lightViewProjection = [];
        this.shadowmaterial = null;
}

Half_Edge_Data.prototype.reverseNormals = function(){

    for(var i=0; i<this.faceArray.length; i++)
        this.faceArray[i].normal = product_vec(-1, this.faceArray[i].normal);
    for(i=0; i<this.vertexArray.length; i++)
        this.vertexArray[i].normal = product_vec(-1, this.vertexArray[i].normal);
}

Half_Edge_Data.prototype.calc_Normals = function(){

    for(var i=0; i<this.faceArray.length; i++){
        this.faceArray[i].calc_Normal();
    }
    for(i=0; i<this.vertexArray.length; i++)
        this.vertexArray[i].calc_Normal();

}

Half_Edge_Data.prototype.constructHalfEdgeStruct = function() {
	
	if(this.indexArray.length>0 && this.posArray.length>0){		
		
		for(var i=0; i<this.posArray.length; i++){			
			var newvertex = new HE_vertex();
                        var pos = this.posArray[i];
                        newvertex.pos = pos;
			this.vertexArray.push(newvertex);
		}		
		
		for(i=0; i<this.indexArray.length/3; i++){
			
			var newFace = new HE_face();
                        newFace.he_calculated = false;
			newFace.setPoint(this.indexArray[i*3]);
			newFace.setPoint(this.indexArray[i*3+1]);
			newFace.setPoint(this.indexArray[i*3+2]);

                        this.faceArray.push(newFace);
			
			var first_edge_exist = false;
			var second_edge_exist = false;
			var third_edge_exist  = false;
			var first_edge = this.vertexArray[this.indexArray[i*3]].getEdge(this.indexArray[i*3], this.indexArray[i*3+1]);
			var second_edge= this.vertexArray[this.indexArray[i*3+1]].getEdge(this.indexArray[i*3+1], this.indexArray[i*3+2]);
			var third_edge = this.vertexArray[this.indexArray[i*3+2]].getEdge(this.indexArray[i*3], this.indexArray[i*3+2]);

                        if(first_edge!=null)
                            first_edge_exist = true;
                        if(second_edge!=null)
                            second_edge_exist = true;
                        if(third_edge!=null)
                            third_edge_exist = true;

			if(first_edge_exist==false){
				var newEdge = new Edge();
				newEdge.directionSet = false;
				newEdge.vertex_IDs.push(this.indexArray[i*3]);
				newEdge.vertex_IDs.push(this.indexArray[i*3+1]);
                                this.vertexArray[this.indexArray[i*3]].edges.push(newEdge);
                                this.vertexArray[this.indexArray[i*3+1]].edges.push(newEdge);
				newEdge.faces.push(newFace);
				this.edgeArray.push(newEdge);
				this.faceArray[i].edges.push(newEdge);
			}
			else {
				this.faceArray[i].edges.push(first_edge);
                                first_edge.faces.push(newFace);
                        }
			if(second_edge_exist==false){
				newEdge = new Edge();
				newEdge.directionSet = false;
				newEdge.vertex_IDs.push(this.indexArray[i*3+1]);
				newEdge.vertex_IDs.push(this.indexArray[i*3+2]);
				this.vertexArray[this.indexArray[i*3+1]].edges.push(newEdge);
                                this.vertexArray[this.indexArray[i*3+2]].edges.push(newEdge);
                                newEdge.faces.push(newFace);
                                this.edgeArray.push(newEdge);
				this.faceArray[i].edges.push(newEdge);
			}
			else{
				this.faceArray[i].edges.push(second_edge);
                                second_edge.faces.push(newFace);
                        }
			if(third_edge_exist==false){
				newEdge = new Edge();
				newEdge.directionSet = false;
				newEdge.vertex_IDs.push(this.indexArray[i*3]);
				newEdge.vertex_IDs.push(this.indexArray[i*3+2]);
                                this.vertexArray[this.indexArray[i*3]].edges.push(newEdge);
                                this.vertexArray[this.indexArray[i*3+2]].edges.push(newEdge);
                                newEdge.faces.push(newFace);
				this.edgeArray.push(newEdge);
				this.faceArray[i].edges.push(newEdge);
			}
			else {
				this.faceArray[i].edges.push(third_edge);
                                third_edge.faces.push(newFace);
                        }
			
			this.vertexArray[this.indexArray[i*3]].addFace(newFace);
			this.vertexArray[this.indexArray[i*3+1]].addFace(newFace);
			this.vertexArray[this.indexArray[i*3+2]].addFace(newFace);				
		}
             for(i=0;i<this.edgeArray.length; i++){
                 if(this.edgeArray[i].faces.length==0){
                   this.edgeArray[i].faces.push(null);
                   this.edgeArray[i].faces.push(null);
                 }
                 else if(this.edgeArray[i].faces.length==1)
                     this.edgeArray[i].faces.push(null);
             }
	}
	
}


Half_Edge_Data.prototype.constructHalfEdges = function(){

	for(var i=0; i<this.edgeArray.length; i++){
		
		var first_half_edge = new HE_edge();
		var second_half_edge= new HE_edge();

                var source_vert = this.vertexArray[Number(this.edgeArray[i].firstVertexID())];
                var dest_vert   = this.vertexArray[Number(this.edgeArray[i].secondVertexID())];


		first_half_edge.setEdge(source_vert, dest_vert);
		second_half_edge.setEdge(dest_vert, source_vert);
		
		first_half_edge.edgeID = i;
		second_half_edge.edgeID = i;
                first_half_edge.edge = this.edgeArray[i];
                second_half_edge.edge= this.edgeArray[i];
		
		this.half_edgeArray.push(first_half_edge);
		this.half_edgeArray.push(second_half_edge);
		
		first_half_edge.setOpposite(second_half_edge);
		second_half_edge.setOpposite(first_half_edge);
		first_half_edge.setDirection = false;
		second_half_edge.setDirection= false;
                this.edgeArray[i].HE_edges.push(first_half_edge);
                this.edgeArray[i].HE_edges.push(second_half_edge);
	}
}

Half_Edge_Data.prototype.makeEdgesCircler = function(faceEdges){
	
	
	
}

Half_Edge_Data.prototype.makeCircle = function(e1, e2, e3, face){
     var h1, h2, h3;

     if(e1.HE_edges[0].face==face){
        h1 = e1.HE_edges[0];

       // h1.getOpposite().setFace();
    }
    else if(e1.HE_edges[1].face==face)
        h1 = e1.HE_edges[1];
    if(e2.HE_edges[0].face==face)
        h2 = e2.HE_edges[0];
    else if(e2.HE_edges[1].face==face)
        h2 = e2.HE_edges[1];
    if(e3.HE_edges[0].face==face)
        h3 = e3.HE_edges[0];
    else if(e3.HE_edges[1].face==face)
        h3 = e3.HE_edges[1];

    if(h1.next_vertex==h2.vertex)
        h1.next = h2;
    else if(h1.next_vertex==h3.vertex)
        h1.next = h3;

    if(h2.next_vertex==h1.vertex)
        h2.next = h1;
    else if(h2.next_vertex==h3.vertex)
        h2.next = h3;

    if(h3.next_vertex==h1.vertex)
        h3.next = h1;
    else if(h3.next_vertex==h2.vertex)
        h3.next = h2;
}

Half_Edge_Data.prototype.calc_Edge_Directions = function(face){
	
	
	if(face==null)
		return;
	if(face.he_calculated)
		return;
	
        if(face.he_calculated==false){
            
            var edge_1 = face.getEdge(0);
            var edge_2 = face.getEdge(1);
            var edge_3 = face.getEdge(2);
            var half_edge_1, half_edge_2, half_edge_3;
            if(edge_1.directionSet && edge_2.directionSet && edge_3.directionSet){
                    this.makeCircle(edge_1, edge_2, edge_3, face);
                    face.he_calculated = true;
                    return;
            }
            else if(edge_1.directionSet && edge_2.directionSet && !edge_3.directionSet){
                    edge_2.setDirection(edge_2, edge_3, face);
                    this.makeCircle(edge_1, edge_2, edge_3, face);
                    face.he_calculated = true;
                    if(edge_3.faces[0]!=null && edge_3.faces[0]!=face)
                            this.calc_Edge_Directions(edge_3.faces[0]);
                    else if(edge_3.faces[1]!=null && edge_3.faces[1]!=face)
                            this.calc_Edge_Directions(edge_3.faces[1]);                    
            }
            else if(edge_1.directionSet && !edge_2.directionSet && edge_3.directionSet){
                    edge_1.setDirection(edge_1, edge_2, face);
                    this.makeCircle(edge_1, edge_2, edge_3, face);
                    face.he_calculated = true;
                    if(edge_2.faces[0]!=null && edge_2.faces[0]!=face)
                            this.calc_Edge_Directions(edge_2.faces[0]);
                    else if(edge_2.faces[1]!=null && edge_2.faces[1]!=face)
                            this.calc_Edge_Directions(edge_2.faces[1]);                    
            }
            else if(!edge_1.directionSet && edge_2.directionSet && edge_3.directionSet){
                    edge_2.setDirection(edge_2, edge_1, face);
                    this.makeCircle(edge_1, edge_2, edge_3, face);
                    face.he_calculated = true;
                    if(edge_1.faces[0]!=null && edge_1.faces[0]!=face)
                            this.calc_Edge_Directions(edge_1.faces[0]);
                    else if(edge_1.faces[1]!=null && edge_1.faces[1]!=face)
                            this.calc_Edge_Directions(edge_1.faces[1]);
            }

            else if(!edge_1.directionSet && !edge_2.directionSet && edge_3.directionSet){
                    edge_3.setDirection(edge_3, edge_1, face);
                    edge_3.setDirection(edge_3, edge_2, face);
                    this.makeCircle(edge_1, edge_2, edge_3, face);
                    face.he_calculated = true;
                    if(edge_1.faces[0]!=null && edge_1.faces[0]!=face)
                            this.calc_Edge_Directions(edge_1.faces[0]);
                    else if(edge_1.faces[1]!=null && edge_1.faces[1]!=face)
                            this.calc_Edge_Directions(edge_1.faces[1]);

                    if(edge_2.faces[0]!=null && edge_2.faces[0]!=face)
                            this.calc_Edge_Directions(edge_2.faces[0]);
                    else if(edge_2.faces[1]!=null && edge_2.faces[1]!=face)
                            this.calc_Edge_Directions(edge_2.faces[1]);
            }
            else if(!edge_1.directionSet && edge_2.directionSet && !edge_3.directionSet){
                    edge_2.setDirection(edge_2, edge_1, face);
                    edge_2.setDirection(edge_2, edge_3, face);
                    this.makeCircle(edge_1, edge_2, edge_3, face);
                    face.he_calculated = true;
                    if(edge_1.faces[0]!=null && edge_1.faces[0]!=face)
                            this.calc_Edge_Directions(edge_1.faces[0]);
                    else if(edge_1.faces[1]!=null && edge_1.faces[1]!=face)
                            this.calc_Edge_Directions(edge_1.faces[1]);

                    if(edge_3.faces[0]!=null && edge_3.faces[0]!=face)
                            this.calc_Edge_Directions(edge_3.faces[0]);
                    else if(edge_3.faces[1]!=null && edge_3.faces[1]!=face)
                            this.calc_Edge_Directions(edge_3.faces[1]);                    
            }
            else if(edge_1.directionSet && !edge_2.directionSet && !edge_3.directionSet){
                    edge_1.setDirection(edge_1, edge_2, face);
                    edge_1.setDirection(edge_1, edge_3, face);
                    this.makeCircle(edge_1, edge_2, edge_3, face);
                    face.he_calculated = true;
                    if(edge_2.faces[0]!=null && edge_2.faces[0]!=face)
                            this.calc_Edge_Directions(edge_2.faces[0]);
                    else if(edge_2.faces[1]!=null && edge_2.faces[1]!=face)
                            this.calc_Edge_Directions(edge_2.faces[1]);

                    if(edge_3.faces[0]!=null && edge_3.faces[0]!=face)
                            this.calc_Edge_Directions(edge_3.faces[0]);
                    else if(edge_3.faces[1]!=null && edge_3.faces[1]!=face)
                            this.calc_Edge_Directions(edge_3.faces[1]);     

            }
            else if(!edge_1.directionSet && !edge_2.directionSet && !edge_3.directionSet){

               var first_half_edge  = face.getHalfEdge(this.vertexArray[face.getPoint(0)], this.vertexArray[face.getPoint(1)]);
               var second_half_edge = face.getHalfEdge(this.vertexArray[face.getPoint(1)], this.vertexArray[face.getPoint(2)]);
               var third_half_edge  = face.getHalfEdge(this.vertexArray[face.getPoint(2)], this.vertexArray[face.getPoint(0)]);

               if(first_half_edge!=null && second_half_edge!=null && third_half_edge!=null){
                   var face_1 = first_half_edge.edge.getFace(0);
                   var face_2 = first_half_edge.edge.getFace(1);

                   if(face_1==face)
                      first_half_edge.getOpposite().setFace(face_2);
                  else{
                      first_half_edge.setFace(face);
                      first_half_edge.getOpposite().setFace(face_1);
                  }

                  face_1 = second_half_edge.edge.getFace(0);
                  face_2 = second_half_edge.edge.getFace(1);

                  if(face_1==face)
                      second_half_edge.getOpposite().setFace(face_2);
                  else{
                      second_half_edge.setFace(face);
                      second_half_edge.getOpposite().setFace(face_1);
                  }


                  face_1 = third_half_edge.edge.getFace(0);
                  face_2 = third_half_edge.edge.getFace(1);

                  if(face_1==face)
                      third_half_edge.getOpposite().setFace(face_2);
                  else{
                      third_half_edge.setFace(face);
                      third_half_edge.getOpposite().setFace(face_1);
                  }

                  first_half_edge.setFace(face);
                  second_half_edge.setFace(face);
                  third_half_edge.setFace(face);
                  //first_half_edge.setNext(second_half_edge);
                  //second_half_edge.setNext(third_half_edge);
                  //third_half_edge.setNext(first_half_edge);
                  edge_1.setDirection(edge_1, edge_2, face);
                  this.makeCircle(edge_1, edge_2, edge_3, face);
                  edge_1.directionSet = true;
                  edge_2.directionSet = true;
                  edge_3.directionSet = true;
                  face.he_calculated = true;

              }
            }
        }


	if(face.edges[0].getFace(0)!=face)
		this.calc_Edge_Directions(face.edges[0].getFace(0));
	else if(face.edges[0].getFace(1)!=face)
		this.calc_Edge_Directions(face.edges[0].getFace(1));
		
	if(face.edges[1].getFace(0)!=face)
		this.calc_Edge_Directions(face.edges[1].getFace(0));
	else if(face.edges[1].getFace(1)!=face)
		this.calc_Edge_Directions(face.edges[1].getFace(1));
	
	if(face.edges[2].getFace(0)!=face)
		this.calc_Edge_Directions(face.edges[2].getFace(0));
	else if(face.edges[2].getFace(1)!=face)
		this.calc_Edge_Directions(face.edges[2].getFace(1));

}

 var positionArray = [0,0,0,0,0,1,1,0,1,1,0,0,0,1,0,0,1,1,1,1,1,1,1,0];

  // The following array stores the texture coordinates (u, v) for each vertex.
  // These coordinates are used by the shader when displaying the texture image
  // on the mesh triangles.


  // The following array defines how vertices are to be put together to form
  // the triangles that make up the cube's faces.  In the index array, every
  // three elements define a triangle.  So for example vertices 0, 1 and 2
  // make up the first triangle, vertices 0, 2 and 3 the second one, etc.
  var indicesArray = [
      0, 1, 2,
      0, 2, 3,
      4, 5, 6,
      4, 6, 7,
      0, 1, 5,
      0, 4, 5,
      0, 3, 4,
      3, 4, 7,
      2, 3, 6,
      3, 6, 7,
      1, 2, 6,
      1, 5, 6
  ];


function construct_sphere(width, height, radius, pos)
{
  var PI = 3.14159265358979323846;
  var theta, phi;
  var i, j, t, ntri, nvec;

  var dat = [];
  var idx = [];

  nvec = (height-2)* width+2;
  ntri = (height-2)*(width-1)*2;

  var newHalf_Edge_Data = new Half_Edge_Data();

  for( t=0, j=1; j<height-1; j++ ){
      for(      i=0; i<width; i++ ){
        theta = (j)/(height-1)*PI;
        phi   = (i)/(width-1 )*PI*2;
        var x = Math.sin(theta)*Math.cos(phi)*radius+pos[0];
        var y = Math.cos(theta)*radius+pos[1];
        var z = -Math.sin(theta)*Math.sin(phi)*radius+pos[2];
        newHalf_Edge_Data.posArray.push([x,y,z]);
     }
  }
  var pnt_1 = [pos[0], pos[1]+radius, pos[2]];
  var pnt_2 = [pos[0], pos[1]-radius, pos[2]];
  newHalf_Edge_Data.posArray.push(pnt_1);
  newHalf_Edge_Data.posArray.push(pnt_2);
  
  for( t=0, j=0; j<height-3; j++ ){
    for(i=0; i<width-1; i++ ){
        newHalf_Edge_Data.indexArray.push(j*width + i);
        newHalf_Edge_Data.indexArray.push((j+1)*width + i+1);
        newHalf_Edge_Data.indexArray.push(j*width + i+1);
        newHalf_Edge_Data.indexArray.push(j*width + i);
        newHalf_Edge_Data.indexArray.push((j+1)*width + i);
        newHalf_Edge_Data.indexArray.push((j+1)*width + i+1);
    }
  }
  for(i=0; i<width-1; i++ ){
    newHalf_Edge_Data.indexArray.push((height-2)*width);
    newHalf_Edge_Data.indexArray.push(i);
    newHalf_Edge_Data.indexArray.push(i+1);
    newHalf_Edge_Data.indexArray.push((height-2)*width+1);
    newHalf_Edge_Data.indexArray.push((height-3)*width + i+1);
    newHalf_Edge_Data.indexArray.push((height-3)*width + i);
  }
/*
  for(i=0; i<positionArray.length/3; i++){
        var x = positionArray[i*3];
        var y = positionArray[i*3+1];
        var z = positionArray[i*3+2];
       newHalf_Edge_Data.posArray.push([x,y,z]);
  }
  
  for(i=0; i<indicesArray.length; i++ ){
     newHalf_Edge_Data.indexArray.push(indicesArray[i]);
  }
  */
  newHalf_Edge_Data.constructHalfEdgeStruct();
  newHalf_Edge_Data.constructHalfEdges();
  newHalf_Edge_Data.calc_Edge_Directions(newHalf_Edge_Data.faceArray[0]);

  newHalf_Edge_Data.pos = pos;
  newHalf_Edge_Data.calc_Normals();

  newHalf_Edge_Data.reverseNormals();

  return newHalf_Edge_Data;


}



function createBaseAtoms(mesh, pack, o3d, client, atomtype, colorMaterial, shadowMaterial){

     var sphereTransform = pack.createObject('Transform');
     sphereTransform.translate(mesh.pos[0], mesh.pos[1], mesh.pos[2]);
     sphereTransform.parent = client.root;


     for(var i=0; i<mesh.faceArray.length; i++){
        var g_triangleTransform;
        var curr_face = mesh.faceArray[i];
        var curr_color= mesh.color;
        var vert_1 = mesh.vertexArray[curr_face.getPoint(0)].pos;
        var vert_2 = mesh.vertexArray[curr_face.getPoint(1)].pos;
        var vert_3 = mesh.vertexArray[curr_face.getPoint(2)].pos;
        var vertices = [vert_1, vert_2, vert_3];
        var normals = [mesh.vertexArray[curr_face.getPoint(0)].normal, mesh.vertexArray[curr_face.getPoint(1)].normal, mesh.vertexArray[curr_face.getPoint(2)].normal];
	// create the Shape for the triangle mesh and assign its material
	var triangleShape = CreateTriangle(pack, o3d, vertices, normals, colorMaterial);

        if(atomtype=='C'){
            c_atomTriangleShape.push(triangleShape);
        }
        else if(atomtype=='O'){
           o_atomTriangleShape.push(triangleShape);
        }
        else if(atomtype=='N'){
            n_atomTriangleShape.push(triangleShape);
        }
        else if(atomtype=='H'){
            h_atomTriangleShape.push(triangleShape);
        }

       var boundingBox = o3d.BoundingBox([0, 0, 0],[0, 0, 0]);


        // create a new transform for the triangle
	g_triangleTransform = pack.createObject('Transform');
	g_triangleTransform.addShape(triangleShape);

        /*var elements = triangleShape.elements;
        var box = elements[0].boundingBox;
        for (var ee = 1; ee < elements.length; ee++){
           box = box.add(elements[ee].boundingBox);
        }
        */
        g_triangleTransform.boundingBox = boundingBox;

       // g_triangleTransform.shape.createDrawElements(pack, shadowMaterial);

	// connect the triangle's transform to the client root
	g_triangleTransform.parent = sphereTransform;
        g_triangleTransform.cull    = true;
        g_triangleTransform.visible = false;
        mesh.transforms.push(g_triangleTransform);
     }

     boundingBox = o3d.BoundingBox([0,0,0], [0,0,0]);
     sphereTransform.boundingBox = boundingBox;
     sphereTransform.cull = false;
     sphereTransform.visible = true;

}


function createSphere(center, pack, o3d, client, atomtype){

    var sphereTransform =  pack.createObject('Transform');
    sphereTransform.translate(center[0], center[1], center[2]);
    sphereTransform.parent = client.root;

    var triangleShapes;
    if(atomtype=='C')
       triangleShapes = c_atomTriangleShape;
    else if(atomtype=='O')
        triangleShapes = o_atomTriangleShape;
    else if(atomtype=='H')
        triangleShapes = h_atomTriangleShape;
    else if(atomtype=='N')
        triangleShapes = n_atomTriangleShape;

   for(var i=0; i<triangleShapes.length; i++){

       var g_triangleTransform = pack.createObject('Transform');
       g_triangleTransform.addShape(triangleShapes[i]);

       var elements = triangleShapes[i].elements;
       var box =  o3d.BoundingBox([0, 0, 0], [0, 0, 0]);


       // for (var ee = 1; ee < elements.length; ee++) {
       //         box = box.add(elements[ee].boundingBox);
       // }
              // Set the transform to have a bounding box that is the sum
              // of all the elements under it.
        g_triangleTransform.boundingBox = box;


       g_triangleTransform.parent = sphereTransform;
       g_triangleTransform.cull   = true;
       g_triangleTransform.visible= true;
       //mesh.transform.push(g_triangleTransform);
   }

   sphereTransform.cull = true;
   sphereTransform.visible = true;
}



//function create_O3D_Mesh(mesh, pack, o3d, client, viewInfo)

function create_O3D_Mesh(mesh, pack, o3d, client, atomtype,colorMaterial, shadowMaterial){
    
   // var transform = pack.createObject('Transform');
   // transform.translate(mesh.pos[0], mesh.pos[1], mesh.pos[2]);

    for(var i=0; i<mesh.faceArray.length; i++){
        
          // -- TRIANGLE --
	// create an Effect object and initialize it using the shaders from the text area
	var g_triangleTransform;
        //var colorEffect = pack.createObject('Effect');
	//var shaderString = triangle_shader_material;
	//colorEffect.loadFromFXString(shaderString);

	// create a Material for the mesh
	//var triangleMaterial = pack.createObject('Material');

	// set the material's drawList, performance draw list is for opaque elements
	//triangleMaterial.drawList = viewInfo.performanceDrawList;
        //triangleMaterial.effect = colorEffect;
        //colorEffect.createUniformParameters(triangleMaterial);
	// apply our effect to this material, it determines which shaders to use

       // triangleMaterial.getParam('lightViewProjection').value = mesh.lightViewProjection;
        //triangleMaterial.getParam('lightWorldPos').value = mesh.lightWorldPos;
        //triangleMaterial.getParam('lightColor').value = mesh.lightColor;
        //triangleMaterial.getParam('ambient').value = o3djs.math.mulScalarVector(0.1, mesh.color);
        //triangleMaterial.getParam('diffuse').value = o3djs.math.mulScalarVector(0.8, mesh.color);
        //triangleMaterial.getParam('specular').value = mesh.specular;
        //triangleMaterial.getParam('shininess').value = 80;

        var curr_face = mesh.faceArray[i];
        var curr_color= mesh.color;
        var vert_1 = mesh.vertexArray[curr_face.getPoint(0)].pos;
        var vert_2 = mesh.vertexArray[curr_face.getPoint(1)].pos;
        var vert_3 = mesh.vertexArray[curr_face.getPoint(2)].pos;
        var vertices = [vert_1, vert_2, vert_3];
        var normals = [mesh.vertexArray[curr_face.getPoint(0)].normal, mesh.vertexArray[curr_face.getPoint(1)].normal, mesh.vertexArray[curr_face.getPoint(2)].normal];
	// create the Shape for the triangle mesh and assign its material
	var triangleShape = CreateTriangle(pack, o3d, vertices, normals, colorMaterial);

	// create a new transform for the triangle
	g_triangleTransform = pack.createObject('Transform');
	g_triangleTransform.addShape(triangleShape);

       // g_triangleTransform.shape.createDrawElements(pack, shadowMaterial);

	// connect the triangle's transform to the client root
	g_triangleTransform.parent = client.root;
        g_triangleTransform.cull = true;
        g_triangleTransform.visible = true;
        mesh.transforms.push(g_triangleTransform);
        
    }

  for(var i=0; i<mesh.faceArray.length; i++){
      delete mesh.faceArray[mesh.faceArray.length-i-1];
  }
  for(var i=0; i<mesh.edgeArray.length; i++){
      delete mesh.edgeArray[mesh.edgeArray.length-i-1];
  }
  for(var i=0; i<mesh.half_edgeArray.length; i++){
      delete mesh.half_edgeArray[mesh.half_edgeArray.length-i-1];
  }
  for(var i=0; i<mesh.posArray.length; i++){
      delete mesh.posArray[mesh.posArray.length-i-1];
  }



  delete mesh.faceArray;
  delete mesh.half_edgeArray;
  delete mesh.vertexArray;
  delete mesh.indexArray;
  delete mesh.posArray;
   
}

var triangle_shader_material = "/**\n" +
   "* This shader is for the effect applied in the second render pass when the\n" +
   "* shadowed shapes are drawn to the screen.  In the pixel shader, the distance\n" +
   "* from the rendered point to the camera is compared to the distance encoded\n" +
   "* in the shadow map.  If the distance is much greater, the rendered point is\n" +
   "* considered to be in shadow and is given a light coefficient of 0.\n" +
   "*/\n" +

  "float4x4 world : World;\n" +
  "float4x4 worldViewProjection : WorldViewProjection;\n" +
  "float4x4 worldInverseTranspose : WorldInverseTranspose;\n" +
  "float4x4 viewInverse : ViewInverse;\n" +
  "float4x4 lightViewProjection;\n" +
  "sampler shadowMapSampler;\n" +
  "\n" +
  "// Parameters for the phong shader.\n" +
  "uniform float3 lightWorldPos;\n" +
  "uniform float4 lightColor;\n" +
  "uniform float4 ambient;\n" +
  "uniform float4 diffuse;\n" +
  "uniform float4 specular;\n" +
  "uniform float shininess;\n" +
  "\n" +
  "// input parameters for our vertex shader\n" +
  "struct VertexShaderInput {\n" +
  "  float4 position : POSITION;\n" +
  "  float3 normal : NORMAL;\n" +
  "};\n" +
  "\n" +
  "// input parameters for our pixel shader\n" +
  "struct PixelShaderInput {\n" +
  "  float4 position : POSITION;\n" +
  "  float4 projTextureCoords : TEXCOORD0;\n" +
  "  float4 worldPosition : TEXCOORD1;\n" +
  "  float3 normal : TEXCOORD2;\n" +
  "};\n" +
  "\n" +
  "PixelShaderInput vertexShaderFunction(VertexShaderInput input) {\n" +
  "  PixelShaderInput output;\n" +
  "\n" +
  "  // Transform to homogeneous clip space.\n" +
  "  output.position = mul(input.position, worldViewProjection);\n" +
  "\n" +
  "  // Compute the projective texture coordinates to project the shadow map\n" +
  "  // onto the scene.\n" +
  "  float4x4 worldLightViewProjection = mul(world, lightViewProjection);\n" +
  "  output.projTextureCoords = mul(input.position, worldLightViewProjection);\n" +
  "  output.worldPosition = mul(input.position, world);\n" +
  "  output.normal = mul(float4(input.normal, 0), worldInverseTranspose).xyz;\n" +
  "\n" +
  " return output;\n" +
  "}\n" +
  "\n" +
  "float4 pixelShaderFunction(PixelShaderInput input): COLOR {\n" +
  "  float3 surfaceToLight = normalize(lightWorldPos - input.worldPosition);\n" +
  "  float3 surfaceToView = normalize(viewInverse[3].xyz - input.worldPosition);\n" +
  " float3 normal = normalize(input.normal);\n" +
  "  float3 halfVector = normalize(surfaceToLight + surfaceToView);\n" +
  "  float4 litResult = lit(dot(normal, surfaceToLight),\n" +
  "                         dot(normal, halfVector), shininess);\n" +
  "  float4 outColor = ambient;\n" +
  "  float4 projCoords = input.projTextureCoords;\n" +
  "\n" +
  "  // Convert texture coords to [0, 1] range.\n" +
  "  projCoords.xy /= projCoords.w;\n" +
  "  projCoords.x =  0.5 * projCoords.x + 0.5;\n" +
  "  projCoords.y = -0.5 * projCoords.y + 0.5;\n" +
  "\n" +
  "  // Compute the pixel depth for shadowing.\n" +
  "  float depth = projCoords.z / projCoords.w;\n" +
  "\n" +
  "  // If the rednered point is farter from the light than the distance encoded\n" +
  "  // in the shadow map, we give it a light coefficient of 0.\n" +
  "  float light = tex2D(shadowMapSampler, projCoords.xy).r + 0.008 > depth;\n" +
  "\n" +
  "  // Make the illuninated area a round spotlight shape just for fun.\n" +
  "  // Comment this line out to see just the shadows.\n" +
  "  light *= 1 - smoothstep(0.45, 0.5, length(projCoords - float2(0.5, 0.5)));\n" +
  "\n" +
  "  outColor += light * lightColor *\n" +
  "      (diffuse * litResult.y + specular * litResult.z);\n" +
  "  return outColor;\n" +
  "}\n" +
  "\n" +
  "// #o3d VertexShaderEntryPoint vertexShaderFunction\n" +
  "// #o3d PixelShaderEntryPoint pixelShaderFunction\n" +
  "// #o3d MatrixLoadOrder RowMajor\n";


function CreateTriangle(pack, o3d, vertices, normals, colorMaterial) //material=shader, color[4],
{
	// create a Shape object for the mesh
	var newShape = pack.createObject('Shape');

	// create the Primitive that will contain the geometry data for the triangle
	var newPrimitive = pack.createObject('Primitive');

	// create a StreamBank to hold the streams of vertex data
	var streamBank = pack.createObject('StreamBank');

	// assign the material that was passed in to the primitive
	newPrimitive.material = colorMaterial;

	// assign the Primitive to the Shape
	newPrimitive.owner = newShape;

	// assign the StreamBank to the Primitive.
	newPrimitive.streamBank = streamBank;

	// a list made by 1 triangle
	newPrimitive.primitiveType = o3d.Primitive.TRIANGLELIST;
	newPrimitive.numberPrimitives = 1; // 1 triangle
	newPrimitive.numberVertices = 3;

	// generate the draw element for the cube primitive
	// null material -> use the material on this Element
	newPrimitive.createDrawElement(pack, null);

	// vertexes data
	var vertexesArray =
	[
		vertices[0][0], vertices[0][1], vertices[0][2], // vertex 0	BOTTOM-LEFT
		vertices[1][0], vertices[1][1], vertices[1][2], // vertex 1	BOTTOM-RIGHT
		vertices[2][0], vertices[2][1], vertices[2][2]  // vertex 2	TOP-CENTER
	];

	// Create buffers containing the vertex data
	var positionsBuffer = pack.createObject('VertexBuffer');
	var positionsField = positionsBuffer.createField('FloatField', 3);
	positionsBuffer.set(vertexesArray);

	// associate the positions Buffer with the StreamBank
	streamBank.setVertexStream( o3d.Stream.POSITION, // semantic: This stream stores vertex positions
								0,                     // semantic index: First (and only) position stream
								positionsField,        // field: the field this stream uses
								0);                    // start_index: How many elements to skip in the field
         var normalArray = [
             normals[0][0], normals[0][1], normals[0][2],
             normals[1][0], normals[1][1], normals[1][2],
             normals[2][0], normals[2][1], normals[2][2],
         ];

        var NormalBuffer = pack.createObject('VertexBuffer');
        var NormalField  = NormalBuffer.createField('FloatField', 3);
        NormalBuffer.set(normalArray);

        streamBank.setVertexStream(o3d.Stream.NORMAL, 0, NormalField, 0);


	return newShape;
}