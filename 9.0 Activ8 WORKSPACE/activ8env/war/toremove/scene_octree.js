/********************************************************/
/* AABB-triangle overlap test code                      */
/* by Tomas Akenine-Möller                              */
/* Function: int triBoxOverlap(float boxcenter[3],      */
/*          float boxhalfsize[3],float triverts[3][3]); */
/* History:                                             */
/*   2001-03-05: released the code in its first version */
/*   2001-06-18: changed the order of the tests, faster */
/*                                                      */
/* Acknowledgement: Many thanks to Pierre Terdiman for  */
/* suggestions and discussions on how to optimize code. */
/* Thanks to David Hunt for finding a ">="-bug!         */
/********************************************************/


var X = 0;
var Y = 1;
var Z = 2;

var EPSILON = 0.000001;
var TEST_CULL = 0;


function dummy_var(){

    this.value = 0;
}

function CROSS(dest,v1,v2){
          dest[0]=v1[1]*v2[2]-v1[2]*v2[1];
          dest[1]=v1[2]*v2[0]-v1[0]*v2[2];
          dest[2]=v1[0]*v2[1]-v1[1]*v2[0];
}


function DOT(v1,v2){
    return (v1[0]*v2[0]+v1[1]*v2[1]+v1[2]*v2[2]);
}

function SUB(dest,v1,v2){
          dest[0]=v1[0]-v2[0];
          dest[1]=v1[1]-v2[1];
          dest[2]=v1[2]-v2[2];
}

function FINDMINMAX(x0,x1,x2,min,max){
  min = max = x0;
  if(x1<min)
      min=x1;
  if(x1>max)
      max=x1;
  if(x2<min)
      min=x2;
  if(x2>max)
      max=x2;
}

function planeBoxOverlap(normal, d, maxbox)  //float normal[3],float d, float maxbox[3]
{
  var q;
  var vmin = new Array(3);
  var vmax = new Array(3);

for(q=X;q<=Z;q++)
  {
    if(normal[q]>0.0)
    {
      vmin[q]=-Number(maxbox[q]);
      vmax[q]=Number(maxbox[q]);
    }
    else
    {
      vmin[q]=Number(maxbox[q]);
      vmax[q]=-Number(maxbox[q]);
    }
  }
  if(DOT(normal,vmin)+d>0.0)
      return 0;
  if(DOT(normal,vmax)+d>=0.0)
      return 1;

  return 0;
}

//checks if the point is in the circle in 3D
function pointCircleIntersection(curr_pnt, circle_center, circle_radius){
    if((curr_pnt[1] < circle_center[1]) || (curr_pnt[1]>circle_center[1]))
        return false;
    var dist = distance(curr_pnt, circle_center);
    if(dist>circle_radius)
        return false;
    return true;
}


/*======================== X-tests ========================*/
function AXISTEST_X01(a, b, fa, fb){
    var p0 = a*v0[Y] - b*v0[Z];
    var p2 = a*v2[Y] - b*v2[Z];
        if(p0<p2) {min=p0;
            max=p2;}
        else {
            min=p2;
            max=p0;
        }
    rad = fa * boxhalfsize[Y] + fb * boxhalfsize[Z];
    if(min>rad || max<-rad)
            return 0;
        return 1;
}

function AXISTEST_X2(a, b, fa, fb){
    var p0 = a*v0[Y] - b*v0[Z];
    var p1 = a*v1[Y] - b*v1[Z];
        if(p0<p1) {
            min=p0;
            max=p1;
        }
        else {
            min=p1;
            max=p0;
        }
    rad = fa * boxhalfsize[Y] + fb * boxhalfsize[Z];
    if(min>rad || max<-rad)
            return 0;
        return 1;
}


/*======================== Y-tests ========================*/
function AXISTEST_Y02(a, b, fa, fb){
    var p0 = -a*v0[X] + b*v0[Z];
    var p2 = -a*v2[X] + b*v2[Z];
        if(p0<p2) {
            min=p0;
            max=p2;
        }
        else {
            min=p2;
            max=p0;
        }
    rad = fa * boxhalfsize[X] + fb * boxhalfsize[Z];
    if(min>rad || max<-rad)
            return 0;
     return 1;
}

function AXISTEST_Y1(a, b, fa, fb){
    var p0 = -a*v0[X] + b*v0[Z];
    var p1 = -a*v1[X] + b*v1[Z];
        if(p0<p1) {
            min=p0;
            max=p1;
        }
        else {
            min=p1;
            max=p0;
        }
    rad = fa * boxhalfsize[X] + fb * boxhalfsize[Z];
    if(min>rad || max<-rad)
            return 0;
    return 1;
}

/*======================== Z-tests ========================*/

function AXISTEST_Z12(a, b, fa, fb){
    var p1 = a*v1[X] - b*v1[Y];
    var p2 = a*v2[X] - b*v2[Y];
        if(p2<p1) {
            min=p2;
            max=p1;
        }
        else {
            min=p1;
            max=p2;
        }
    rad = fa * boxhalfsize[X] + fb * boxhalfsize[Y];
    if(min>rad || max<-rad)
            return 0;
        return 1;
}

function AXISTEST_Z0(a, b, fa, fb){
    var p0 = a*v0[X] - b*v0[Y];
    var p1 = a*v1[X] - b*v1[Y];
        if(p0<p1) {
            min=p0;
            max=p1;
        }
        else {
            min=p1;
            max=p0;
        }
    rad = fa * boxhalfsize[X] + fb * boxhalfsize[Y];
    if(min>rad || max<-rad)
            return 0;
        return 1;
}
//calculates the closest point on a segment [AB] to a Point P
function GetClosetPoint( A, B, P, segmentClamp){
    var AP = [P[0]-A[0], P[1]-A[1], P[2]-A[2]];
    var AB = [B[0]-A[0], B[1]-A[1], B[2]-A[2]];
    var ab2 = AB[0]*AB[0] + AB[1]*AB[1] + AB[2]*AB[2];
    var ap_ab = AP[0]*AB[0] + AP[1]*AB[1] + AP[2]*AB[2];
    var t = ap_ab/ab2;
    if (segmentClamp)
    {
         if (t < 0.0)
             t = 0.0;
         else if(t > 1.0)
             t = 1.0;
    }
    var Closest = [A[0] + AB[0]*t, A[1] + AB[1]*t, A[2] + AB[2]*t];
    return Closest;
}


function AABB(){
    this.center = []; //Center of the AABB
    this.radius = []; //radius or halfwidth extends(x,y,z)
    this.type   = "";
    this.translate = null;
    this.velocity = [0, 0, 0];
    this.acceleration = [0, 0, 0];
    this.mass       = 1;
    this.Interaction_Objects = [];
    this.octree_nodes = [];  //the octree leaves which referances to this node
    this.getCorner = function (i){
        var r = this.radius;
        var c = this.center;
        if(i==0)
            return [c[0]-r[0], c[1]-r[1], c[2]-r[2]];
        else if(i==1)
            return [c[0] + r[0], c[1]-r[1], c[2]-r[2]];
        else if(i==2)
            return [c[0]+r[0], c[1]-r[1], c[2]+r[2]];
        else if(i==3)
            return [c[0]-r[0], c[1]-r[1], c[2]+r[2]];
        else if(i==4)
            return [c[0]-r[0], c[1]+r[1], c[2]-r[2]];
        else if(i==5)
            return [c[0]+r[0], c[1]+r[1], c[2]-r[2]];
        else if(i==6)
            return [c[0]+r[0], c[1]+r[1], c[2]+r[2]];
        else if(i==7)
            return [c[0]-r[0], c[1]+r[1], c[2]+r[2]];
        return [];
    }

     this.getVolume = function(){
        var a = distance(this.getCorner(0), this.getCorner(1));
        var b = distance(this.getCorner(0), this.getCorner(3));
        var c = distance(this.getCorner(0), this.getCorner(4))
        return a*b*c;
    }

    this.getFace_Vertex_Indices = function(){
        return [[0,1,2,3], [0,1,4,5], [1,2,5,6], [2,3,6,7], [3,0,7,4], [4,5,6,7]];

    }
}

function intersect_AABB_AABB(box_1, box_2){
    if (Math.abs(box_1.center[0] - box_2.center[0]) > (box_1.radius[0] + box_2.radius[0]))
        return 0;
    if (Math.abs(box_1.center[1] - box_2.center[1]) > (box_1.radius[1] + box_2.radius[1]))
        return 0;
    if (Math.abs(box_1.center[2] - box_2.center[2]) > (box_1.radius[2] + box_2.radius[2]))
        return 0;
    return 1;
}

//If the octree_nodes element is not empty it cleans deletes the reference of this bounding box object
//from that octants reference list
function delete_Referances(box_1){
   for(var i=0; i<box_1.octree_nodes.length; i++){

        var BB_list = box_1.octree_nodes[i].membrane_AABBs;
        var new_list = [];
        for(var j=0; j<BB_list.length; j++){
            if(BB_list[j]!=box_1)
                new_list.push(BB_list[j]);
        }
        box_1.octree_nodes[i].membrane_AABBs = new_list;
   }
   box_1.octree_nodes = [];
}

//http://hq.scene.ro/blog/read/circle-box-intersection-revised/
//Checks the intersection of a circle with an AABB
function intersect_Circle_AABB(curr_node, circle_center, circle_radius, zone){

    var collisionDetected = false;
    switch ( zone ) {
        // top and bottom side zones
        // check vertical distance between centers
        case 1:
        case 7:
            var distZ = Math.abs(circle_center[2] - curr_node.center[2]);
            if ( distZ <= ( circle_radius + curr_node.radius[2]))
                collisionDetected = true;
        break;
        // left and right side zones. check distance between centers
        // check horizontal distance between centers
        case 3:
        case 5:
            var distX = Math.abs( circle_center[0] - curr_node.center[0]);
            if ( distX <= ( circle_radius + curr_node.radius[0]) ) {
                collisionDetected = true;
            }
        break;
        // inside zone. collision for sure
        case 4:
            collisionDetected = true;
        break;
        // corner zone.
        // get the corner and check if inside the circle
        default:
            var cornerX = ( zone == 0 || zone == 6 ) ?
                  curr_node.center[0]  - curr_node.radius[0] : curr_node.center[0] + curr_node.radius[0];
            var cornerZ = ( zone == 0 || zone == 2 ) ?
                  curr_node.center[2]  - curr_node.radius[2] : curr_node.center[2] + curr_node.radius[2];

            if (  pointCircleIntersection([cornerX, circle_center[1], cornerZ], circle_center, circle_radius) ) {
                collisionDetected = true;
            }
        break;
    }
    return collisionDetected;
}

function is_inside_Point(curr_pnt, box){
    if (Math.abs(box.center[0] - curr_pnt[0])> box.radius[0]+0.001)
        return false;
    if (Math.abs(box.center[1] - curr_pnt[1]) > box.radius[1]+0.001)
        return false;
    if (Math.abs(box.center[2] - curr_pnt[2]) > box.radius[2]+0.001)
        return false;
    return true;

}




function get_Intersection_Volume_AABB(box_1, box_2){
    //Cases are, one box can be totally in the other one
    //only one corner of each box can in the other one, so we have two extrem pointsof the crossing volume AABB
    //the last case can be two or four corners of one box in the other one, if this is the case,
    //we are finding the projection of these inside points on the faces of the bigger box
    //then if these projected points are on the smaller box, they are on the crossing volume.
    //Two extreme most distant points of these will be used to calculate the intersection volume.

    var box_1_inside = false;  //fully inside the other box
    var box_1_inside_corners = [];
    var box_2_inside = false;  //fully inside the box_1
    var box_2_inside_corners = [];
    var partial_intersect = false;
    var m = 0;
    var max_x=-100000, min_x=100000;
    var max_y=-100000, min_y=100000;
    var max_z=-100000, min_z=100000;
    for(var i=0; i<8; i++){
        var curr_corner = box_1.getCorner(i);
        var inside = is_inside_Point(curr_corner, box_2);
        if(inside){
            if(max_x<curr_corner[0]) max_x = curr_corner[0];
            if(min_x>curr_corner[0]) min_x = curr_corner[0];
            if(max_y<curr_corner[1]) max_y = curr_corner[1];
            if(min_y>curr_corner[1]) min_y = curr_corner[1];
            if(max_z<curr_corner[2]) max_z = curr_corner[2];
            if(min_z>curr_corner[2]) min_z = curr_corner[2];
            m++;
            box_1_inside_corners.push(curr_corner);
        }
    }
    if(m==8)
        return box_1.getVolume();

    m=0;
 
    for(var i=0; i<8; i++){
        var curr_corner = box_2.getCorner(i);
        var inside = is_inside_Point(curr_corner, box_1);
        if(inside){
            if(max_x<curr_corner[0]) max_x = curr_corner[0];
            if(min_x>curr_corner[0]) min_x = curr_corner[0];
            if(max_y<curr_corner[1]) max_y = curr_corner[1];
            if(min_y>curr_corner[1]) min_y = curr_corner[1];
            if(max_z<curr_corner[2]) max_z = curr_corner[2];
            if(min_z>curr_corner[2]) min_z = curr_corner[2];
            m++;
            box_2_inside_corners.push(curr_corner);
        }
    }

    if(m==8)
        return box_2.getVolume();

    if(box_1_inside_corners.length==1 || box_2_inside_corners.length==1){
        var a = Math.abs(box_1_inside_corners[0][0] - box_2_inside_corners[0][0]);
        var b = Math.abs(box_1_inside_corners[0][1] - box_2_inside_corners[0][1]);
        var c = Math.abs(box_1_inside_corners[0][2] - box_2_inside_corners[0][2]);
        return a*b*c;
    }
    else if(box_1_inside_corners.length==2 || box_1_inside_corners.length==4){
        var all_Corners_AABB = [];
        for(var i=0; i<8; i++)
            all_Corners_AABB.push(box_1.getCorner(i));
        var face_indices = box_2.getFace_Vertex_Indices(); //get all the indices of the faces of box_2
        var projected_points = [];
        for(var k=0; k<box_1_inside_corners.length; k++){
            for(var i=0; i<6; i++){
                var pnt_1 = box_2.getCorner(face_indices[i][0]);
                var pnt_2 = box_2.getCorner(face_indices[i][1]);
                var pnt_3 = box_2.getCorner(face_indices[i][2]);
                var inside_pnt = box_1_inside_corners[k];
                var curr_projected_pnt = closestPoint_onPolygon(inside_pnt, pnt_1, pnt_2, pnt_3 );
                projected_points.push(curr_projected_pnt);
            }
        }
        var max_dist = 0;
        var point_1 = null;
        var point_2 = null;
        var points_on_box_1 = [];
         //now check which projected points are on the box_2
        for(var i=0; i<projected_points.length; i++){
            var is_inside = is_inside_Point(projected_points[i], box_1);
             //now calculate the two farhest points projected point
            if(is_inside){
                points_on_box_1.push(projected_points[i]);
                if(points_on_box_1.length==1){
                    points_on_box_1.push(box_1_inside_corners[0]);
                    max_dist = distance(points_on_box_1[0], points_on_box_1[1]);
                    point_1 = points_on_box_1[0];
                    point_2 = points_on_box_1[1];
                }
                else if(points_on_box_1.length>2){
                    for(var m=0; m<points_on_box_1.length; m++){
                        var dist = distance(points_on_box_1[m], points_on_box_1[points_on_box_1.length-1]);
                        if(dist>max_dist){
                            point_1 = points_on_box_1[m];
                            point_2 = points_on_box_1[points_on_box_1.length-1];
                            max_dist = dist;
                        }
                    }
                }
            }
        }
         if(point_1!=null && point_2!=null){
            var a = Math.abs(point_1[0] - point_2[0]);
            var b = Math.abs(point_1[1] - point_2[1]);
            var c = Math.abs(point_1[2] - point_2[2]);
            return a*b*c;
         }
         return 0;
    }
    else if(box_2_inside_corners.length==2 || box_2_inside_corners.length==4){
        var all_Corners_AABB = [];
         for(var i=0; i<8; i++)
            all_Corners_AABB.push(box_2.getCorner(i));
        var face_indices = box_1.getFace_Vertex_Indices(); //get all the indices of the faces of box_2
        var projected_points = [];
        for(var k=0; k<box_2_inside_corners.length; k++){
            for(var i=0; i<6; i++){
                var pnt_1 = box_1.getCorner(face_indices[i][0]);
                var pnt_2 = box_1.getCorner(face_indices[i][1]);
                var pnt_3 = box_1.getCorner(face_indices[i][2]);
                var inside_pnt = box_2_inside_corners[k];      //there is a problem in here
                var curr_projected_pnt = closestPoint_onPolygon(inside_pnt, pnt_1, pnt_2, pnt_3 );
                projected_points.push(curr_projected_pnt);
            }
        }
        //now check which projected points are on the box_1
        var max_dist = 0;
        var point_1 = null;
        var point_2 = null;
        var points_on_box_2 = [];
         //now check which projected points are on the box_2
        for(var i=0; i<projected_points.length; i++){
            var is_inside = is_inside_Point(projected_points[i], box_2);
             //now calculate the two farhest points projected point
            if(is_inside){
                points_on_box_2.push(projected_points[i]);
                if(points_on_box_2.length==1){
                    points_on_box_2.push(box_2_inside_corners[0]);
                    max_dist = distance(points_on_box_2[0], points_on_box_2[1]);
                    point_1 = points_on_box_2[0];
                    point_2 = points_on_box_2[1];
                }
                else if(points_on_box_2.length>2){
                    for(var m=0; m<points_on_box_2.length; m++){
                        var dist = distance(points_on_box_2[m], points_on_box_2[points_on_box_2.length-1]);
                        if(dist>max_dist){
                            point_1 = points_on_box_2[m];
                            point_2 = points_on_box_2[points_on_box_2.length-1];
                            max_dist = dist;
                        }
                    }
                }
            }
        }
        if(point_1!=null && point_2!=null){
            var a = Math.abs(point_1[0] - point_2[0]);
            var b = Math.abs(point_1[1] - point_2[1]);
            var c = Math.abs(point_1[2] - point_2[2]);
            return a*b*c;
        }
        return 0;
    }


    if(partial_intersect)
        return (max_x - min_x)*(max_y-min_y)*(max_z-min_z);

    return 0;  // the axis aligned bounding boxes are not crossing each other
}

//for test if the ray is crossing the triangle, but it does not calculating the
//intersection point
function intersect_triangle(orig, dir, vert0, vert1, vert2, t,u,v){

var edge1 = new Array(3);
var edge2 = new Array(3);
var tvec  = new Array(3);
var pvec  = new Array(3);
var qvec  = new Array(3);

//find vectors for two edges sharing vert
SUB(edge1, vert1, vert0);
SUB(edge2, vert2, vert0);

/*begin calculating determinant  also used to calculate U parameter */
CROSS(pvec,dir,edge2);

/*If determinant is near zero, ray lies in plane of triangle*/
if(TEST_CULL){
        if(det<EPSILON) //define TEST_CULL if culling is desired
           return 0;
        /*calculate distance from vert0 to ray orgin*/
        SUB(tvec, orig, vert0);

        //calculate U parameter and test bounds
        u = DOT(tvec, pvec);
        if(u<0.0 || u>det)
            return 0;
        //prepare to test V parameter
        CROSS(qvec, tvec, edge1);
        /*Calculate V parameter and test bounds*/
        v = DOT(dir, qvec);
        if(v<0.0 || u+v>det)
            return 0;
        /*calculate t, scale parameters, ray intersect triangle*/
        t = DOT(edge2, qvec);
        inv_det = 1.0/det;
        t *=inv_det;
        u *=inv_det;
        v *=inv_det;
    }
    else{  //the non-culling branch
        if(det>-EPSILON && det<EPSILON)
            return 0;
        inv_det = 1.0/det;
        /* calculate distance from vert0 to ray origin */
        SUB(tvec, orig, vert0);
        /*Calculate U parameter and test bounds*/

        u = DOT(tvec, pvec)*inv_det;
        if(u<0.0 || u>1.0)
            return 0;
        /* Prepare to test V parameter*/
        CROSS(qvec, tvec, edge1);
        //calculate V parameter and test bounds
        v = DOT(dir, qvec)*inv_det;
        if(v<0 || u+v>1.0)
            return 0;
        /*Calculate t, ray  intersects triangle*/
        t = DOT(edge2, qvec)*inv_det;
    }
    return 1;
}

function triBoxOverlap( boxcenter, boxhalfsize, triverts){  //float boxcenter[3],float boxhalfsize[3],float triverts[3][3]

  /*    use separating axis theorem to test overlap between triangle and box */
  /*    need to test for overlap in these directions: */
  /*    1) the {x,y,z}-directions (actually, since we use the AABB of the triangle */
  /*       we do not even need to test these) */
  /*    2) normal of the triangle */
  /*    3) crossproduct(edge from tri, {x,y,z}-directin) */
  /*       this gives 3x3=9 more tests */
   var v0 = new Array(3);
   var v1 = new Array(3);
   var v2 = new Array(3);
   var axis =  new Array(3);
   var min,max,d,p0,p1,p2,rad,fex,fey,fez;
   var normal = new Array(3);
   var e0 = new Array(3);
   var e1 = new Array(3);
   var e2 = new Array[3];

   /* This is the fastest branch on Sun */
   /* move everything so that the boxcenter is in (0,0,0) */
   SUB(v0,triverts[0],boxcenter);
   SUB(v1,triverts[1],boxcenter);
   SUB(v2,triverts[2],boxcenter);

   /* compute triangle edges */
   SUB(e0,v1,v0);      /* tri edge 0 */
   SUB(e1,v2,v1);      /* tri edge 1 */
   SUB(e2,v0,v2);      /* tri edge 2 */

   /* Bullet 3:  */
   /*  test the 9 tests first (this was faster) */
   fex = fabs(e0[X]);
   fey = fabs(e0[Y]);
   fez = fabs(e0[Z]);
   AXISTEST_X01(e0[Z], e0[Y], fez, fey);
   AXISTEST_Y02(e0[Z], e0[X], fez, fex);
   AXISTEST_Z12(e0[Y], e0[X], fey, fex);

   fex = fabs(e1[X]);
   fey = fabs(e1[Y]);
   fez = fabs(e1[Z]);
   AXISTEST_X01(e1[Z], e1[Y], fez, fey);
   AXISTEST_Y02(e1[Z], e1[X], fez, fex);
   AXISTEST_Z0(e1[Y], e1[X], fey, fex);

   fex = fabs(e2[X]);
   fey = fabs(e2[Y]);
   fez = fabs(e2[Z]);
   AXISTEST_X2(e2[Z], e2[Y], fez, fey);
   AXISTEST_Y1(e2[Z], e2[X], fez, fex);
   AXISTEST_Z12(e2[Y], e2[X], fey, fex);

   /* Bullet 1: */
   /*  first test overlap in the {x,y,z}-directions */
   /*  find min, max of the triangle each direction, and test for overlap in */
   /*  that direction -- this is equivalent to testing a minimal AABB around */
   /*  the triangle against the AABB */

   /* test in X-direction */
   FINDMINMAX(v0[X],v1[X],v2[X],min,max);
   if(min>boxhalfsize[X] || max<-boxhalfsize[X])
       return 0;

   /* test in Y-direction */
   FINDMINMAX(v0[Y],v1[Y],v2[Y],min,max);
   if(min>boxhalfsize[Y] || max<-boxhalfsize[Y])
       return 0;

   /* test in Z-direction */
   FINDMINMAX(v0[Z],v1[Z],v2[Z],min,max);
   if(min>boxhalfsize[Z] || max<-boxhalfsize[Z])
       return 0;

   /* Bullet 2: */
   /*  test if the box intersects the plane of the triangle */
   /*  compute plane equation of triangle: normal*x+d=0 */
   CROSS(normal,e0,e1);
   d=-DOT(normal,v0);  /* plane eq: normal.x+d=0 */
   if(!planeBoxOverlap(normal,d,boxhalfsize))
       return 0;

   return 1;   /* box and triangle overlaps */
}

function get_dist(pnt1, pnt2){
    var pow_x = Math.pow(pnt1[0] - pnt2[0], 2);
    var pow_y = Math.pow(pnt1[1] - pnt2[1], 2);
    var pow_z = Math.pow(pnt1[2] - pnt2[2], 2);
    return Math.sqrt(pow_x + pow_y + pow_z);
}

//a ray orgin and direction, a point on a plane and a plane normal
function intersect(rOrigin, rNormal, pOrigin, pNormal)
{
    // Calc D for the plane (this is usually pre-calculated
    // and stored with the plane)
    var D = DOT(pOrigin, pNormal);

    // Determine the distance from rOrigin to the point of
    // intersection on the plane

    var numer = DOT(pNormal, rOrigin) + D;
    var denom = DOT(pNormal, rNormal);
    return -(numer / denom);
}

//calculates the crossing point of the ray and the plane
function crossing_pnt(rOrigin, rNormal, pOrigin, pNormal)
{
    // Get the distance to the collision point
    var time = intersect(rOrigin, rNormal, pOrigin, pNormal);
    // Calculate the collision point
    var collisionPoint = new Array(3);
    collisionPoint.x = rOrigin[0] + rNormal[0]*time;
    collisionPoint.y = rOrigin[1] + rNormal[1]*time;
    collisionPoint.z = rOrigin[2] + rNormal[2]*time;
    // Here it is...
    return collisionPoint;
}


//returns the projection of a point on a plane, this projected point is actually
//is the closest`point on plane to the point
//Each parameter is an array of three coordinte [x,y,z]
function closestPoint_onPolygon(curr_pnt, cor_a, cor_b, cor_c){
    var vec_1 = subPoints(cor_a, cor_b);
    var vec_2 = subPoints(cor_c, cor_b);
    var normal = crossProduct(vec_1, vec_2);
    normal = normalizeVec3D(normal);
    var d = (normal[0]*cor_a[0] + normal[1]*cor_a[1] + normal[2]*cor_a[2]);

    var t = (dotProduct(normal, curr_pnt) - d)/dotProduct(normal, normal);
    return subPoints(curr_pnt, multiplyPnt(normal, t));
}



//Given a segment ab and point c, computes closest point d on ab.
//Also returns t for the postion of d, d(t) = a + t*(b-a)
function closestPtPointSegment(c, a, b, t, pnt){
    var ab = subPoints(b,a);
    var ca = subPoints(c,a);
    //project c onto ab, computing parameterized position d(t) = a + t*(b-a)
    t.value = dotProduct(ca, ab);
    var m = dotProduct(ab, ab);
    m = 1.0/m;
    t.value = t.value*m;
    //If outside of the segment, clapm t(and therefor d) to the closest endpoint
    if(t.value<0.0)
        t.value = 0.0;
    if(t.value>1.0)
        t.value = 1.0;
    //Compute projected position from the clamped t
    pnt.d = multiplyPnt(ab, t.value);
    pnt.d = addPoints(pnt.d, a);
}

function Vector3(pos_x, pos_y, pos_z){

    this.d = [pos_x,pos_y,pos_z];

    function x() {return this.d[0];}
    function y() {return this.d[1];}
    function z() {return this.d[2];}

   function get_Element(i){return this.d[i];}

   function length(){
       return Math.sqrt(this.d[0]*this.d[0] + this.d[1]*this.d[1] + this.d[2]*this.d[2]);
   }
   function normalize(){
      var temp = this.length();
      if (temp == 0.0)
        return; // 0 length vector
      // multiply by 1/magnitude
      temp = 1 / temp;
      this.d[0] *= temp;
      this.d[1] *= temp;
      this.d[2] *= temp;
    }

    /////////////////////////////////////////////////////////
    // Overloaded operators
    /////////////////////////////////////////////////////////

    function add(op2){   // vector addition
      var newVector = new Vector3(this.d[0] + op2.d[0], this.d[1] + op2.d[1], this.d[2] + op2.d[2]);
      return newVector;
    }
    function subtruct(op2){   // vector subtraction
      var newVector = new Vector3(this.d[0] - op2.d[0], this.d[1] - op2.d[1], this.d[2] - op2.d[2]);
      return newVector;
    }
    function get_minus(){                    // unary minus
      var newVector = new Vector3(-this.d[0], -this.d[1], -this.d[2]);
      return newVector;
    }
    function multily_vec(s){            // scalar multiplication
      var newVector = new Vector3(this.d[0]*s, this.d[1]*s, this.d[2]*s);
      return newVector;
    }
    function multiply(s) {
      this.d[0] *= s;
      this.d[1] *= s;
      this.d[2] *= s;
    }
    function divide(s){            // scalar division
      var newVector = new Vector3(this.d[0]/s, this.d[1]/s, this.d[3]/s);
      return newVector;
    }
    function dot_product(op2){   // dot product
      return this.d[0] * op2.x() + this.d[1] * op2.y() + this.d[2] * op2.z();
    }
    function cross_product(op2){   // cross product
      var newVector = new Vector3(this.y()*op2.z()-this.z()*op2.y(), this.z()*op2.x() - this.x()*op2.z(),
                                  this.x()*op2.y() - this.y()*op2.x());
      return newVector;
    }
    function equal(op2){
      return (this.x()==op2.x() && this.y()==op2.y() && this.z()==op2.z());
    }

    function smaller(op2) {
      return (this.x()<op2.x() && this.y()<op2.y() && this.z()<op2.z());
    }
    function smaller_equal(op2){
       return (this.x()<=op2.x() && this.y()<=op2.y() && this.z()<=op2.z());
    }
}


function Ray(o,d) {
    this.origin = o;
    this.direction = d;
    this.inv_direction = [1/d[0], 1/d[1], 1/d[2]];
    this.sign = [Number(this.inv_direction[0] < 0), Number(this.inv_direction[1] < 0), Number(this.inv_direction[2] < 0)];
}


function cross_Sphere_Cube(radius, x, y, z, max_pnt, min_pnt){

  var closest_x=0;
  var closest_y=0;
  var closest_z=0;

  if(x<min_pnt[0])
      closest_x = min_pnt[0];
  else if(x>max_pnt[0])
      closest_x = max_pnt[0];
  else
      closest_x = x;

  if(y<min_pnt[1])
      closest_y = min_pnt[1];
  else if(y>max_pnt[1])
      closest_y = max_pnt[1];
  else
      closest_y = y;

  if(z<min_pnt[2])
      closest_z = min_pnt[2];
  else if(z>max_pnt[2])
      closest_z = max_pnt[2];
  else
      closest_z = z;

  var dist = Math.sqrt(Math.pow(closest_x - x, 2.0) + Math.pow(closest_y - y, 2.0) + Math.pow(closest_z - z, 2.0));

  if(dist<=radius)
     return true;
 return false;
}



//test the intersection between the cylinder and AABB using sweeping sphere on the medial axis of cylinder
//First find the closest point on the axis of cylinder to the cube center, then put a sphere on that point
//which has a radius same as cylinder. Than test if there is a intersection between this sphere and AABB.
function cross_Cylinder_Cube(cyl_pnt_1, cyl_pnt_2, rad_cylinder, center_AABB, rad_AABB){

    //Given a segment ab and point c, computes closest point d on ab.
    //Also returns t for the postion of d, d(t) = a + t*(b-a)
    var cross_pnt = new Vector3(0,0,0);
    var t = new dummy_var();
    closestPtPointSegment(center_AABB, cyl_pnt_1, cyl_pnt_2, t, cross_pnt);
    var min_pnt = [center_AABB[0]-rad_AABB[0], center_AABB[1]-rad_AABB[1], center_AABB[2]-rad_AABB[2]];
    var max_pnt = [center_AABB[0]+rad_AABB[0], center_AABB[1]+rad_AABB[1], center_AABB[2]+rad_AABB[2]];
    var isCrossing = cross_Sphere_Cube(rad_cylinder, cross_pnt.d[0], cross_pnt.d[1], cross_pnt.d[2], max_pnt, min_pnt);
    delete cross_pnt;
    delete t;
    if(isCrossing)
        return true;
    else return false;
}

//Intersect R(t) = p + t*d against AABB a. When intersecting, return intersection
//distance and point q of intersection
function IntersectRayAABB(p, d, a, tmin, q){  //Point p, Vector d, float &tmin, Point &q

    tmin = 0.0;
    var tmax = 100000.0;
    var max = [a.center[0] + a.radius[0], a.center[1] + a.radius[1], a.center[2] + a.radius[2]];
    var min = [a.center[0] - a.radius[0], a.center[1] - a.radius[1], a.center[2] - a.radius[2]];
    for(var i=0; i<3; i++){
        if(Math.abs(d[i])<EPSILON){
            //Ray is parallel to slab. No hit if origin is not in slab
            if(p[i]<min[i] || p[i]>max[i])
                return 0;
        }
        else{
            //Compute intersection t value of ray with near and far plane slab
            var ood = 1.0/d[i];
            var t1  = (min[i] - p[i])*ood;
            var t2  = (max[i] - p[i])*ood;
            //make the intersection with near plane, t2 with far plane
            if(t1>t2){
                var temp = t2;
                t2 = t1;
                t1 = temp;
            }
            if(t1>tmin)
                tmin = t1;
            if(t2<tmax)
                tmax = t2;
            //exit with no intersection as soon as slab intersection becomes empty
            if(tmin>tmax)
                return 0;
        }
    }
    //Ray intersects all 3 slabs. Return point (q) and intersection t value (tmin)
    var re = multiplyPnt(d, tmin);
    q = addPoints(p, re);
    return 1;
 }

//Intersects ray r = p + td, |d| = 1, with sphere defined by a center and radius, if intersecting,
//returns t value of intersection point
function IntersectRaySphere(point, direct, sphere_center, sphere_rad, t, inter_pnt){

    var m = subPoints(point, sphere_center);
    var b = dotProduct(m, direct);
    var c = dotProduct(m,m) - Math.pow(sphere_rad, 2);
    //exit if r's origin outside s (c>0) and r pointing away from s (b>0)
    if(c>0.0 && b>0.0)
        return 0;
    var discr = Math.pow(b, 2) - c;
   //A negative discriminant corresponds to ray missing sphere
    if(discr<0.0)
        return 0;
   //ray now found to intersect sphere, compute smallest t value of intersection
   t = -b - Math.sqrt(discr);
   //If t is negative, ray started inside sphere so clamp t to zero
   if(t<0.0)
       t = 0.0;
   inter_pnt.point = multiplyPnt( direct, t);
   inter_pnt.point = addPoints(inter_pnt.point, point);
   return 1;
}


//Intersection segment S(t) =  sa + t(sb-sa), 0<=t<=1 against cylinder specified by p, q, and r
function IntersectRayCylinder(point_sa, direct, point_p, point_q, cylinder_rad, inter_pnt){

  //var R = subPoints(point_sb, point_sa);
  //R = normalizeVec3D(R);
  var R = direct;
  var A = subPoints(point_q, point_p);
  A = normalizeVec3D(A);

  var D = crossProduct(R, A);
  var ren = getLength(D);
  D = multiplyPnt(D, 0.1*ren);

  var k =  subPoints(point_sa, point_p);

  var d = Math.abs( dotProduct(k, D));

  if(d>cylinder_rad)
      return 0;

  var max_distance = subPoints(point_q, point_p);
  max_distance = getLength(max_distance);
  max_distance = Math.sqrt(max_distance*max_distance + cylinder_rad*cylinder_rad);

  var t = 0;
  d = subPoints(point_q, point_p);
  //d = normalizeVec3D(d);
  var m = subPoints(point_sa, point_p);
  //m = normalizeVec3D(m);
  var k = multiplyPnt(direct, 5000);
  var n = k;
  //n = normalizeVec3D(n);


  var md = dotProduct(m, d);
  var nd = dotProduct(n, d);
  var dd = dotProduct(d, d);
  //test if segment is  fully outside either endcap of cylinder
  if((md<0.0) && (md+nd)<0.0)
      return 0; //segment outside 'p' side of the cylinder
  if(md>dd && (md + nd)>dd )
      return 0;  //segment outside 'q' side of cylinder
  var nn = dotProduct(n,n);
  var mn = dotProduct(m,n);
  var a  = dd*nn - nd*nd;
  var k  = dotProduct(m,m) - cylinder_rad*cylinder_rad;
  var c  = dd*k - md*md;

  if(Math.abs(a) < EPSILON){
      //segment runs parallel to cylinder axis
      if(c>0.0)
          return 0;
      if(md<0.0) //now know that segment intersects cylinder
          t = -mn/nn; //intersect segment against 'p' endcap
      else if(md>dd)
          t = (nd - mn)/nn; //intersect segment again 'q' endcap
      else
          t = 0.0; //'a' lies inside cylinder
      var re = multiplyPnt(n, t);
      inter_pnt.point = addPoints(point_sa, re);
      var distance1 = get_dist(inter_pnt.point, point_p);
      var distance2 = get_dist(inter_pnt.point, point_q);
      if(max_distance<distance1 || max_distance<distance2)
          return 0;
      return 1;

  }

  var b = dd*mn - nd*md;
  var discr = b*b -a*c;
  if(discr<0.0)
      return 0;  //no real roots no intersection
  var t0 = t = (-b- Math.sqrt(discr)) / a;
  if(t<0.0 || t>1.0)
      return 0; //intersection lies outside of segment

  if(md + t*nd<0.0){
      //intersection lies outside cylinder 'p' side
      if(nd<=0.0)
          return 0; //segmant pointing away from endcap
      t = -md/nd;
      if(k + t * (2.0*mn + t*nn)<=0){  //Keep intersection if Dot(S(t) - p, S(t) - p)<=r^2
          var re = multiplyPnt(n, t);
          inter_pnt.point = addPoints(point_sa, re);
          var distance1 = get_dist(inter_pnt.point, point_p);
          var distance2 = get_dist(inter_pnt.point, point_q);
          if(max_distance<distance1 || max_distance<distance2)
            return 0;
          return 1;
      }
  }
  else if(md + t*nd>dd){
      //intersection lies outside cylinder 'q' side
      if(nd>=0.0 )
          return 0; //segmant pointing away from endcap+
      t = (dd-md)/nd;
      var re = multiplyPnt(n, t);
      inter_pnt.point = addPoints(point_sa, re);
     //Keep intersection if Dot(S(t) - p, S(t) - p)<=r^2
      if(k+dd-2*md + t*(2*(mn-nd) + t*nn)<=0.0)
        return 1;
  }
  //segment intersects between the endcaps, t is correct
  t = t0;
  var re = multiplyPnt(n, t);
  inter_pnt.point = addPoints(point_sa, re);
  var distance1 = get_dist(inter_pnt.point, point_p);
  var distance2 = get_dist(inter_pnt.point, point_q);
  if(max_distance<distance1 || max_distance<distance2)
      return 0;
  return t>=0 || t<=1.0;
}



var allScene_Leafs = []; //holds the leafs of the octree
var crossing_Octants = []; //holds all the crossing leaf octants with a defined sphere;
var subtree_point_count = 0;  //total point number in a sub tree
var subtree_poly_count = 0; //polygons inside the subtree

var crossed_Octants = [];

var current_visibles = [];
var current_unvisibles = [];

var visible_list      = [];
var unvisible_list    = [];
var half_visible_list = [];

var last_cylinder = null;

function sphere_Element(){
    this.atom_ID    = -1;
    this.protein_obj = null;
    this.mode       = -1;
    this.sphere_translate = null;
    this.radius     = 0.2;
    this.already_visited = false;
}

function cylinder_Element(){
    this.atom_IDS = [];
    this.pos_1 = [];
    this.pos_2 = [];
    this.info  = "";
    //this.protein_ID = -1;
    this.protein_obj = null;
    this.mode     = -1;
    this.chain_ID = -1; //in which chain in the protein file
    this.ID  =  -1;     //in which index in the array
    this.cylinder_trans = null;
    this.radius         = 0.2;
    this.already_visited = false;
    this.scale           = [];
    this.rotate          = [];
    this.translate       = [];
    this.rot = [];
    this.trans = [];
    //this.scale = [];
    this.transform_matrix = null;
}

function scene_octree(c, r, curr_level){

    this.center  = c;
    this.radius  = r;   //half distances
    //this.max_pnt = max_point;
    //this.min_pnt = min_point;
    this.level   = curr_level;
    this.spheres = [];
    this.cylinders=[];
    this.atom_IDS = [];
    this.triangles = [];
    this.array_index = -1;
    this.parent    = null;
    this.childs = [null, null, null, null, null, null, null, null];
    this.leaf = false;
    this.neighbours = []; //holds the neighbours of the octree
    this.mode = 0;
    this.visibility = 2; // 0 = unvisible, 1=partly-visible, 2=complately visible
    this.already_visited = false;
   // this.paramaters = new Array(Vector3(min_pnt[0], min_pnt[1], min_pnt[2]), Vector3(max_pnt[0], max_pnt[1], max_pnt[2]));

    this.Sphere_IDS = [];  // holds the sphere IDS [[molecule 1 sphere IDS  ], [molecule_2_spheres], [molecule_3_spheres] ....]
    this.Cylinder_IDS = []; //holds the cylinders' IDS [[molecule 1 sphere IDS  ], [molecule_2_spheres], [molecule_3_spheres] ....]
    this.Triangle_IDS = [];  //holds the triangles' IDS [[molecule 1 sphere IDS  ], [molecule_2_spheres], [molecule_3_spheres] ....]

    this.membrane_AABBs = []; //holds the AABBs of the cell membrane

    this.occupied_volume = 0;
    this.node_volume = 0;

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
    this.isLeaf = function(){
        return this.leaf;
    }
    this.getElementCount = function(){
        return this.spheres.length + this.cylinders.length + this.triangles.length;
    }
    this.setVolume = function(){
        var max = addPoints(this.center, this.radius);
        var min = addPoints(this.center, this.radius);
        this.node_volume = (max[0]-min[0])*(max[1]-min[1])*(max[2]-min[2]);
    }
}

function calc_Tree_Volume(curr_node){
    if(curr_node==null)
        return;
    curr_node.setVolume();
    for(var i=0; i<8; i++)
        calc_Node_Volume(curr_node.childs[i]);
}


//Finds all the membrane_AABBs in a give circle region
var Membrane_Elements_In_Region = [];
function getMembrane_AABBs_in_Region(curr_node, circle_center, circle_radius){
    if(curr_node==null)
        return;
    //if((circle_center[1]<curr_node.center[1]-curr_node.radius[1]) ||
    //    (circle_center[1]>curr_node.center[1]+curr_node.center[1]))
    //return;
    var xZone = circle_center[0] < ( curr_node.center[0] - curr_node.radius[0] ) ? 0 :
                ( circle_center[0] > ( curr_node.center[0] + curr_node.radius[0] ) ? 2 : 1 );
    var zZone = circle_center[2] < ( curr_node.center[2] - curr_node.radius[2] ) ? 0 :
                ( circle_center[2] > ( curr_node.center[2] + curr_node.radius[2] ) ? 2 : 1 );
    var zone =  xZone + 3*zZone;

    var boundingBox = new AABB();
    boundingBox.center = curr_node.center;
    boundingBox.radius = curr_node.radius;
    var collision_Detected = intersect_Circle_AABB(boundingBox, circle_center, circle_radius, zone);
    delete boundingBox;

    if(collision_Detected==false)
        return;

    if(curr_node.isLeaf()){
        for(var i=0; i<curr_node.membrane_AABBs.length; i++){
            var boundingBox = new AABB();
            boundingBox.center = curr_node.membrane_AABBs[i].center;
            boundingBox.radius = curr_node.membrane_AABBs[i].radius;
            
            xZone = circle_center[0] < ( boundingBox.center[0] - boundingBox.radius[0] ) ? 0 :
                ( circle_center[0] > ( boundingBox.center[0] + boundingBox.radius[0] ) ? 2 : 1 );
            zZone = circle_center[2].y < ( boundingBox.center[2] - boundingBox.radius[2] ) ? 0 :
                ( circle_center[2] > ( boundingBox.center[2] + boundingBox.radius[2] ) ? 2 : 1 );
            zone =  xZone + 3*zZone;
            var collision_Detected = intersect_Circle_AABB(boundingBox, circle_center, circle_radius, zone);
            if(collision_Detected)
                Membrane_Elements_In_Region.push(curr_node.membrane_AABBs[i]);
        }
        return;
    }
    for(var i=0; i<8; i++)
        getMembrane_AABBs_in_Region(curr_node.childs[i], circle_center, circle_radius);
    return;
}




// (t0, t1) is the interval for valid hits
//const Ray &r, float t0, float t1
function intersect(r, t0, t1) {

  var tmin, tmax, tymin, tymax, tzmin, tzmax;

  tmin = (parameters[r.sign[0]].x() - r.origin.x()) * r.inv_direction.x();
  tmax = (parameters[1-r.sign[0]].x() - r.origin.x()) * r.inv_direction.x();
  tymin = (parameters[r.sign[1]].y() - r.origin.y()) * r.inv_direction.y();
  tymax = (parameters[1-r.sign[1]].y() - r.origin.y()) * r.inv_direction.y();
  if ( (tmin > tymax) || (tymin > tmax) )
    return false;
  if (tymin > tmin)
    tmin = tymin;
  if (tymax < tmax)
    tmax = tymax;
  tzmin = (parameters[r.sign[2]].z() - r.origin.z()) * r.inv_direction.z();
  tzmax = (parameters[1-r.sign[2]].z() - r.origin.z()) * r.inv_direction.z();
  if ( (tmin > tzmax) || (tzmin > tmax) )
    return false;
  if (tzmin > tmin)
    tmin = tzmin;
  if (tzmax < tmax)
    tmax = tzmax;
  return ( (tmin < t1) && (tmax > t0) );
 }

function makeLeaf(node){

    if(node.childs[0]==null && node.childs[1]==null && node.childs[2]==null && node.childs[3]==null &&
       node.childs[4]==null && node.childs[5]==null && node.childs[6]==null && node.childs[7]==null)
       node.leaf = true;

}

//Creates a new view matrix
function createScene_Octree(node, max_level){

    if(node==null)
        return;
    if(max_level==node.getLevel()){
        return;
    }

    var new_rad = divPoint(node.radius, 2);

    var first  = new scene_octree([node.center[0] - new_rad[0], node.center[1] - new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var second = new scene_octree([node.center[0] - new_rad[0], node.center[1] - new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var third  = new scene_octree([node.center[0] - new_rad[0], node.center[1] + new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var fourth = new scene_octree([node.center[0] - new_rad[0], node.center[1] + new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var fifth  = new scene_octree([node.center[0] + new_rad[0], node.center[1] - new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var sixth  = new scene_octree([node.center[0] + new_rad[0], node.center[1] - new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var seventh= new scene_octree([node.center[0] + new_rad[0], node.center[1] + new_rad[1], node.center[2] + new_rad[2]], new_rad, node.getLevel()+1);
    var eight  = new scene_octree([node.center[0] + new_rad[0], node.center[1] + new_rad[1], node.center[2] - new_rad[2]], new_rad, node.getLevel()+1);
    var children = [first, second, third, fourth, fifth, sixth, seventh, eight];
    for(var i=0; i<8; i++){
         node.childs[i] = children[i];
         node.childs[i].parent = node;
    }

    if(max_level==node.getLevel()+1){
      var len = allScene_Leafs.length;
      for(var i=0; i<8; i++)
        allScene_Leafs.push(node.childs[i]);
      for(var i=0; i<8; i++)
          makeLeaf(node.childs[i]);
      for(var i=0; i<8; i++){
         node.childs[i].array_index = len+i;
      }
      return;
    }
    for(var i=0; i<8; i++)
       createScene_Octree(node.childs[i], max_level);

    return;
}

function putSphere(octreeNode, curr_sphere){

    if(octreeNode==null)
        return;
    var max_pnt = [];
    max_pnt[0] = (octreeNode.center[0] + octreeNode.radius[0]);
    max_pnt[1] = (octreeNode.center[1] + octreeNode.radius[1]);
    max_pnt[2] = (octreeNode.center[2] + octreeNode.radius[2]);
    var min_pnt = [];
    min_pnt[0] = (octreeNode.center[0] - octreeNode.radius[0]);
    min_pnt[1] = (octreeNode.center[1] - octreeNode.radius[1]);
    min_pnt[2] = (octreeNode.center[2] - octreeNode.radius[2]);
    var center = curr_sphere.protein_obj.atoms[curr_sphere.atom_ID-1].getCoord();

    if(octreeNode.isLeaf()){
       // cross_Sphere_Cube(radius, x, y, z, max_pnt, min_pnt)
      var overlap = cross_Sphere_Cube(curr_sphere.radius, center[0], center[1], center[2], max_pnt, min_pnt);
      if(overlap){
          octreeNode.spheres.push(curr_sphere);
      }
      return;
    }

    var overlap = cross_Sphere_Cube(curr_sphere.radius, center[0], center[1], center[2], max_pnt, min_pnt);
    if(overlap){
        for(var i=0; i<8; i++){
            if(octreeNode.childs[i]!=null)
                putSphere(octreeNode.childs[i], curr_sphere);
        }
    }
    return;
}

function putCylinder(octreeNode, cylinder){

	if(octreeNode==null)
		return;

	var protein = cylinder.protein_obj;
	var coord_1;
        var coord_2;
        var rad = cylinder.radius;
    if(cylinder.mode==6){
        coord_1 = cylinder.pos_1;
	coord_2 = cylinder.pos_2;
    }
    else{
        coord_1 = protein.atoms[cylinder.atom_IDS[0]-1].getCoord();
	coord_2 = protein.atoms[cylinder.atom_IDS[1]-1].getCoord();
    }

    var overlap = cross_Cylinder_Cube(coord_1, coord_2, rad, octreeNode.center, octreeNode.radius);
    if(octreeNode.isLeaf()){
    	if(overlap)
    	 	octreeNode.cylinders.push(cylinder);
    	return;
    }

    if(overlap){
	    for(var i=0; i<8; i++){
                 if(octreeNode.childs[i]!=null)
                    putCylinder(octreeNode.childs[i], cylinder);
            }
    }
    return;
}

/*
function putCylinder(octreeNode, rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode){

    if(octreeNode==null)
        return;

   var overlap = cross_Cylinder_Cube(start_pnt, end_pnt, rad, octreeNode.center, octreeNode.radius);
   if(octreeNode.isLeaf()){
       if(overlap){
           var new_Cylinder = new cylinder_Element();
           new_Cylinder.atom_IDS = atom_IDS;
           new_Cylinder.protein_ID = protein_ID;
           new_Cylinder.mode = mode;
           octreeNode.cylinders.push(new_Cylinder);
           last_cylinder = new_Cylinder;
       }
       return;
   }

 if(overlap){
    if(octreeNode.childs[0]!=null){
        putCylinder(octreeNode.childs[0], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
    if(octreeNode.childs[1]!=null){
        putCylinder(octreeNode.childs[1], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
    if(octreeNode.childs[2]!=null){
        putCylinder(octreeNode.childs[2], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
    if(octreeNode.childs[3]!=null){
        putCylinder(octreeNode.childs[3], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
    if(octreeNode.childs[4]!=null){
        putCylinder(octreeNode.childs[4], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
    if(octreeNode.childs[5]!=null){
        putCylinder(octreeNode.childs[5], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
    if(octreeNode.childs[6]!=null){
        putCylinder(octreeNode.childs[6], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
    if(octreeNode.childs[7]!=null){
        putCylinder(octreeNode.childs[7], rad, start_pnt, end_pnt, atom_IDS, protein_ID, mode);
    }
  }
  return;
}
*/
function putTrianngle(octreeNode, triVert, triangle){ //triVert[3][3], triID=ID of the triangle in the mesh, type=sphere, backbone, ribbon, surface...

    if(octreeNode==null)
        return

    //triBoxOverlap( boxcenter, boxhalfsize, triverts){  //float boxcenter[3],float boxhalfsize[3],float triverts[3][3]
   if(octreeNode.isLeaf() && triBoxOverlap(octreeNode.center, octreeNode.radius, triVert)){
       octreeNode.triangles.push(triangle);
       return;
    }

    if(triBoxOverlap(box_center, box_half_size, triVert)){
        for(var i=0; i<8; i++){
            if(octreeNode.childs[i]!=null){
                putTrianngle(octreeNode.childs[i], triVert, triangle);
            }
        }
    }

   return;
}


//function getTriangle_Number(node){

var element_count = 0;
var merged_tri_list = [];
var merged_sphere_list = [];
var merged_cylinder_list = [];
var merged_AABB_list = [];


function merge_element_lists(node){
    if(node==null)
        return;
    else if(node.isLeaf()){
        if(node.triangles.length!=0)
            merged_tri_list = merged_tri_list.concat(node.triangles);
        if(node.spheres.length!=0)
            merged_sphere_list = merged_sphere_list.concat(node.spheres);
        if(node.cylinders.length!=0)
            merged_cylinder_list = merged_cylinder_list.concat(node.cylinder);
        if(node.membrane_AABBs.length!=0)
            merged_AABB_list = merged_AABB_list.concat(node.membrane_AABBs);
    }
    else{
        for(var i=0; i<8; i++){
            if(node.getChild(i)!=null)
                merge_element_lists(node.getChild(i));
        }
    }
}


function getElement_Number(node){ //triangle=0, sphere=1, cylinder=2

    if(node==null)
        return;
    else if(node.isLeaf()){  //if the node is a leaf node
        element_count += node.triangles.length;
        element_count += node.spheres.length;
        element_count += node.cylinders.length;
        element_count += node.membrane_AABBs.length;
        return;
    }

    for(var i=0; i<8; i++){
       if(node.getChild(i)!=null)
            getElement_Number(node.getChild(i));
    }
}



function mergeNodes(node, threshold){

    if(node==null)
        return;
    else if(node.isLeaf())
        return;
    else{
        element_count = 0;
        getElement_Number(node);
        if(element_count<=threshold){
            merged_tri_list = [];
            merged_sphere_list = [];
            merged_cylinder_list = [];
            merged_AABB_list = [];
            merge_element_lists(node);
            node.triangles = merged_tri_list;
            node.spheres   = merged_sphere_list;
            node.cylinders = merged_cylinder_list;
            node.membrane_AABBs = merged_AABB_list;
            node.leaf = true;
            node.childs = [null, null, null, null, null, null, null, null];
        }
        else{
            for(var i=0; i<8; i++){
                if(node.getChild(i)!=null)
                    mergeNodes(node.getChild(i), threshold);
            }
        }
    }
}


function mergeOctree(threshold){

     for(var i=0; i<allScene_Leafs.length; i++){

        if(allScene_Leafs[i]==null)
            continue;

        element_count = 0;
        parent_Node = allScene_Leafs[i].getParent(); //get the parent of the child.
        getElement_Number(parent_Node);  //get  the total points in the subtree

        if(element_count<threshold){
            element_count = 0;
            for(var i=0; i<8; i++){
                getElement_Number(parent_Node.childs[i]);
                if(element_count>0){
                    parent_Node.triangles = parent_Node.triangles.concat(parent_Node.childs[i].triangles);
                    parent_Node.spheres   = parent_Node.spheres.concat(parent_Node.childs[i].sphres);
                    parent_Node.cylinders = parent_Node.cylinders.concat(parent_Node.childs[i].cylinders);
                }
                element_count = 0;
            }

            for(var i=0; i<8; i++)
                 allScene_Leafs[parent_Node.childs[i].getArr_Index()] = null;

            for(var i=0; i<8; i++)
                delete parent_Node.childs[i];

            parent_Node.childs = [null, null, null, null, null, null, null, null];
            allScene_Leafs.push(parent_Node);
            parent_Node.array_index = allScene_Leafs.length-1;
        }
    }

    var allLeaves_temp = [];
    for(i=0; i<allScene_Leafs.length;i++ ){
        if(allScene_Leafs[i]!=null)
           allLeaves_temp.push(allScene_Leafs[i]);
    }
    delete allScene_Leafs;
    allScene_Leafs = allLeaves_temp;
    delete allLeaves_temp;
    for(i=0; i<allScene_Leafs.length; i++){
        allScene_Leafs[i].array_index = i;
        allScene_Leafs[i].leaf = true;
    }
}


function findCrossedNodes(node, ray){

    if(node==null)
        return;

    if(node.isLeaf()==false && node.intersect(ray, 0.0, 1.0)){
        for(var i=0; i<8; i++)
            if(node.getChild(i)!=null)
                findCrossedNodes(node.getChild(i), ray);
    }
    else if(node.isLeaf() && node.intersect(ray, 0.0, 1.0)){
        octants_crossed.push(node);
        return;
    }
    return;
}


function Max(a,b,c){
   var m = a<b ? b : a;
   m = m<c ? c: m;
   return m;
}

function Min(a,b,c){
    var m = a<b ? a : b;
    m = m<c ? m : c;
    return m;
}

var a = 0;
var octants_crossed = [];

//check only the crossed octants by the ray, then according to rendering mode of each protein,
//check the ray and element crossing, find the minimum one and return it
function check_Intersection(ray_orgin, ray_direct){

    var closest_distance = 10000000;
    var crossed_object_type = -1;  //sphere=0, cylinder=1, triandgle=2,
    var crossed_object      = null;
    var inter_pnt = new intersection_pnt();

    for(var i=0; i<octants_crossed.length; i++){

        var spheres  = octants_crossed[i].spheres
        var cylinders= octants_crossed[i].cylinders;


        for(var j=0; j<spheres.length; j++){
            if(spheres[j]==null)
                continue;
            if(spheres[j].sphere_translate==null)
                continue;
            //check if the sphere is visible
            var protein = spheres[j].protein_obj;
            var atom_id = spheres[j].atom_ID;
            var atom = protein.atoms[atom_id-1];
            var mode = protein.protein_render_mode;
            var element = atom.element;
            var atom_name = atom.atomName;

            var rad = 0;
            if(mode==0) //if this protein is in "render only atoms" mode
              rad = atomTypes[atom.element];
            else if(mode==1) //if this protein is in "render sticks and atoms" mode
              rad = 0.3;
            else if(mode==2 || mode==7) //if this protein is in "render only sticks" mode
              rad = 0.2;
             else if(mode==3 || mode==7){ //if this protein is in "render only backbone"
                if(atom_name!="CA")
                continue;
              rad = 0.2;
             }
             else if(mode==4 || mode==7){ //render only minimal backbone
              if(element!="C" || element!="N" || element!="CA")
                continue;
              rad = 0.2;
             }
             else if(mode==5 || mode==7){ //render only maximum backbone
               if(element!="C" || element!="N" || element!="CA" || element!="O")
                  continue;
               rad = 0.2;
             }
             else if(mode==8){
                 rad=0.1;
             }
           var t;
           var visible = false;
           if(mode==0 && spheres[j].sphere_translate.visible)
               visible = true;
           else if(mode==1 && spheres[j].sphere_translate.visible)
               visible = true;
           else if(((mode>1 && mode<=5) || (mode==7 || mode==8)) && spheres[j].sphere_translate.visible)
               visible = true;
           /*
           if(mode==0 && protein.all_spheres[(atom_id-1)*3].sphere_translate.visible)
               visible = true;
           else if(mode==1 && protein.all_spheres[(atom_id-1)*3+1].sphere_translate.visible)
               visible = true;
           else if(((mode>1 && mode<=5) || mode==7) && protein.all_spheres[(atom_id-1)*3+1].sphere_translate.visible)
               visible = true;
            */
          /*if(protein.transforms_atom.length==0)
               continue;
           if(protein.transforms_atom[atom_id-1].visible && mode==0)
             visible = true;
           else if(protein.transforms_atom_sticks[atom_id-1].visible && mode==1)
             visible = true;
           else if(protein.stick_sphere_transforms[atom_id-1].visible && (mode==2 || mode==3 || mode==4 || mode==5 || mode==6))
           	 visible = true;
           */
           if(visible){
               var coord = atom.getCoord();
               var interset_pnt = new intersection_pnt();
               var b_intersect = IntersectRaySphere(ray_orgin, ray_direct, atom.getCoord(), rad, t, interset_pnt);
               if(b_intersect){
                    var dist = get_dist(interset_pnt.point, ray_orgin);
                    if(dist<closest_distance){
                        closest_distance = dist;
                        crossed_object_type = 0;
                        crossed_object = spheres[j];
                    }
               }
           }

        }

        for(var j=0; j<cylinders.length; j++){
            var mode = cylinders[j].protein_obj.protein_render_mode;
            if(mode==0) // it is in render only atoms mode, no need to check cylinders
                break;
            if(mode!=7){
                if(cylinders[j]==null || cylinders[j].cylinder_trans==null)
                    continue;
                else if(cylinders[j]==null || cylinders[j].cylinder_trans.visible==false)
                    continue;
            }
            var b_intersect = 0;
            if(mode==6 && cylinders[j].mode==6)
                b_intersect= IntersectRayCylinder(ray_orgin, ray_direct, cylinders[j].pos_1, cylinders[j].pos_2, cylinders[j].radius, inter_pnt);

            if(b_intersect){
                //check if the cylinder is visible
                //protein.
                var dist = get_dist(inter_pnt.point, ray_orgin);
                if(dist<closest_distance){
                   crossed_object_type = 1;
                   crossed_object = cylinders[j];
                }
                continue;
            }
             if(cylinders[j].mode==6 && !b_intersect)
                 continue;
             if((mode==7 || mode==6) && cylinders[j].mode==6)
                 continue;
            var curr_cylinder = cylinders[j];
            var first_atom_ID = cylinders[j].atom_IDS[0];
            var second_atom_ID= cylinders[j].atom_IDS[1];
            var protein = cylinders[j].protein_obj;
            var atom_1 = protein.atoms[first_atom_ID-1];
            var atom_2 = protein.atoms[second_atom_ID-1];

            if(mode==0)
                break;
            var element = atom_1.element;
            var atom_name = atom_1.atomName;
            var rad = 0;
            var end_Coord = addPoints(atom_1.getCoord(), atom_2.getCoord());
            end_Coord = multiplyPnt(end_Coord, 0.5);
            var chain_ID = atom_1.chainNumber;
            var index    = cylinders[j].ID;

            if(mode==3 && atom_1.element=="CA" && atom_2.element=="CA"){ //render in chain mode
                if(atom_1.element!="CA" || atom_2.element!="CA")
                    continue;
            }
            else if(mode==4){  //render in minimal backbone mode
                if(atom_1.element!="CA" || atom_1.element!="N" || atom_1.element!="C")
                    continue;
                if(atom_2.element!="CA" || atom_2.element!="N" || atom_2.element!="C")
                    continue;
            }
            else if(mode==5){   //render in full backbone mode
                if((atom_1.element!="C" || atom_2.element!="O") || (atom_1.element!="O" || atom_1.element!="C"))
                    continue;
            }
            else if(mode==1 || mode==2){
                if(atom_1.element=="CA" && atom_2.element=="CA")
                    continue;
            }
            else if(mode==6 && cylinders[j].mode!=6)
                continue;

            if(mode==7 || mode==8)
                b_intersect= IntersectRayCylinder(ray_orgin, ray_direct, atom_1.getCoord(), end_Coord, 0.1, inter_pnt);
            else if(mode!=6)
                b_intersect= IntersectRayCylinder(ray_orgin, ray_direct, atom_1.getCoord(), end_Coord, cylinders[j].radius, inter_pnt);

            if(b_intersect){
                //check if the cylinder is visible
                //protein.
                var dist = get_dist(inter_pnt.point, ray_orgin);
                if(dist<closest_distance){
                   crossed_object_type = 1;
                   crossed_object = cylinders[j];
                }
            }
        }
    }

    var return_str = "";
    if(crossed_object!=null){       //the closest geometrical object is a sphere
       if(crossed_object_type==0){
            var protein = crossed_object.protein_obj;
            var atom_id = crossed_object.atom_ID;
            var protein_file = protein.filename;
            var atom = protein.atoms[atom_id-1];
            var element = atom.element;
            var atom_ID = atom.ID;
            last_PDB = protein;
            return_str = protein_file + " atom_ID: " + atom.ID + " " + atom.element + " " + atom.resedueName;
       }
       else if(crossed_object_type==1 && crossed_object.mode!=6){         //the closest geometrical object is a cylinder
            var curr_cylinder = crossed_object;
            var first_atom_ID = crossed_object.atom_IDS[0];
            var second_atom_ID= crossed_object.atom_IDS[1];
            var protein = crossed_object.protein_obj;
            var atom_1 = protein.atoms[first_atom_ID-1];
            var atom_2 = protein.atoms[second_atom_ID-1];
            var mode = protein.protein_render_mode;
            var element = atom.element;
            var atom_name = atom.atomName;
            var rad = 0;
            var distance = get_dist(atom_1.getCoord(), atom_2.getCoord());
            last_PDB = protein;
            return_str = protein.filename  + " " + atom_1.resedueName + " " + atom_1.resedueNumber + "." + atom_1.chainName + " " + atom_1.atomName;
            return_str+= "--" + atom_2.resedueName + " " + atom_2.resedueNumber + "." + atom_2.chainName + " " + atom_2.atomName + " " + distance;
       }
       else if(crossed_object_type==1 && crossed_object.mode==6){ //if the crossed object is a ribbon
           return_str = crossed_object.info;
       }
       else
            return_str = "";
    }

    delete inter_pnt;
    return return_str;
}


// In practice, it may be worth passing in the ray by value or passing in a copy of the ray
// because of the fact the ray_step() function is destructive to the ray data.
function ray_step( node, ray_orgin, ray_direct ){
    a = 0;
    //var new_ray = new Ray(ray_orgin, ray_direct);
    var q = [0,0,0];
    var t = 0;
    if(node==null)
        return;

    var intersect = IntersectRayAABB(ray_orgin, ray_direct, node, t, q);

    if(intersect==1 && node.isLeaf()){
        if(node.getElementCount()>0)
            octants_crossed.push(node);
        return;
    }
    else if(intersect==1 && !node.isLeaf()){
        for(var i=0; i<8; i++)
             ray_step(node.childs[i], ray_orgin, ray_direct);
    }

    //findCrossedNodes(node, new_ray);
    //delete new_ray;
    return ;

    if (ray_direct[0] < 0)
    {
        ray_orgin[0] = node.radius[0]*2.0 - ray_orgin[0];
        ray_direct[0] = -1*ray_direct[0];
        a |= 4;
    }
    if (ray_direct[1] < 0)
    {
        ray_orgin[1] = node.radius[1]*2 - ray_orgin[1];
        ray_direct[1] = -1*ray_direct[1];
        a |= 2;
    }
    if(ray_direct[2]<0)
    {
        ray_orgin[2]  = node.radius[2]*2 - ray_orgin[2];
        ray_direct[2] = -1*ray_direct[2];
        a |= 1;
    }

    var tx0 = ((node.center[0] - node.radius[0]) - ray_orgin[0])/ray_direct[0];
    var tx1 = ((node.center[0] + node.radius[0]) - ray_orgin[0])/ray_direct[0];
    var ty0 = ((node.center[1] - node.radius[1]) - ray_orgin[1])/ray_direct[1];
    var ty1 = ((node.center[1] + node.radius[1]) - ray_orgin[1])/ray_direct[1];
    var tz0 = ((node.center[2] - node.radius[2]) - ray_orgin[2])/ray_direct[2];
    var tz1 = ((node.center[2] + node.radius[2]) - ray_orgin[2])/ray_direct[2];

    var tmin = Max(tx0,ty0,tz0);
    var tmax = Min(tx1,ty1,tz1);

    if ( tmin < tmax )
        proc_subtree(tx0,ty0,tz0,tx1,ty1,tz1, node);
}

/*
note on cell indices: they are ordered according the formula
index = x+(y*3)+(z*9)
*/

function find_firstNode(tx0, ty0, tz0, txm, tym, tzm){

    var max = Max(tx0,ty0,tz0);

    var index = 0;
    if(max==tx0){//YZ plane
        if(tym<tx0)
            index = index | 2;
        if(tzm<tx0)
            index = index | 4;
    }
    else if(max==ty0){//XZ plane
        if(txm<ty0)
            index = index | 1;
        if(tzm<ty0)
            index = index | 4;
    }
    else if(max==tz0){//XY plane
        if(txm<tz0)
            index = index | 1;
        if(tym<tz0)
            index = index | 2;
    }
    return index;
}


function next_Node(txm, tym, tzm, a, b, c){

  var min = Min(txm,tym,tzm);

    if(txm==min)
        return a; //exits through yz plane
    else if(tym==min)
        return b; //xz
    else
        return c; //xy
}

function find_nextNode(tx3, ty3, tz3, x, y, z)
{
    min = Menger::Min(tx3,ty3,tz3);

    if(tx3==min)
        return x; //exits through yz plane
    else if(ty3==min)
        return y; //xz
    else
        return z; //xy
    return x;
}


function proc_subtree( tx0, ty0, tz0, tx1, ty1, tz1, node)
{
    var currNode;
    if(node==null)
        return;
    if ( (tx1 <= 0.0 ) || (ty1 <= 0.0) || (tz1 <= 0.0) )
        return;

    if (node.isLeaf()){
        octants_crossed.push(node);
        return;
    }

    var txM = 0.5 * (tx0 + tx1);
    var tyM = 0.5 * (ty0 + ty1);
    var tzM = 0.5 * (tz0 + tz1);

    // Determining the first node requires knowing which of the t0's is the largest...
    // as well as comparing the tM's of the other axes against that largest t0.
    // Hence, the function should only require the 3 t0-values and the 3 tM-values.

    var first_node_indx = find_firstNode(tx0,ty0,tz0,txM,tyM,tzM);
    currNode = node.childs[first_node_indx];


    do {
        // next_Node() takes the t1 values for a child (which may or may not have tM's of the parent)
        // and determines the next node.  Rather than passing in the currNode value, we pass in possible values
        // for the next node.  A value of 8 refers to an exit from the parent.
        // While having more parameters does use more stack bandwidth, it allows for a smaller function
        // with fewer branches and less redundant code.  The possibilities for the next node are passed in
        // the same respective order as the t-values.  Hence if the first parameter is found as the greatest, the
        // fourth parameter will be the return value.  If the 2nd parameter is the greatest, the 5th will be returned, etc.
        switch(first_node_indx) {
        case 0 :
                    proc_subtree(tx0,ty0,tz0,txM,tyM,tzM,node.childs[a]);
                    first_node_indx = next_Node(txM,tyM,tzM,4,2,1);
                    //if(currNode!=null)
                    //    currNode = currNode.childs[first_node_indx];
                    break;
        case 1 :
                    proc_subtree(tx0,ty0,tzM,txM,tyM,tz1,node.childs[1^a]);
                    first_node_indx = next_Node(txM,tyM,tz1,5,3,8);
                    //if(currNode!=null)
                    //    currNode = currNode.childs[first_node_indx];
                    break;
        case 2 :
                    proc_subtree(tx0,tyM,tz0,txM,ty1,tzM,node.childs[2^a]);
                    first_node_indx = next_Node(txM,ty1,tzM,6,8,3);
                    //if(currNode!=null)
                    //    currNode = currNode.childs[first_node_indx];
                    break;
        case 3 :
                    proc_subtree(tx0,tyM,tzM,txM,ty1,tz1,node.childs[3^a]);
                    first_node_indx = currNode = next_Node(txM,ty1,tz1,7,8,8);
                    //if(currNode!=null)
                    //    currNode = currNode.childs[first_node_indx];
                    break;
        case 4 :
                    proc_subtree(txM,ty0,tz0,tx1,tyM,tzM,node.childs[4^a]);
                    first_node_indx = next_Node(tx1,tyM,tzM,8,6,5);
                    //if(currNode!=null)
                    //    currNode = currNode.childs[first_node_indx];
                    break;
        case 5 :
                    proc_subtree(txM,ty0,tzM,tx1,tyM,tz1,node.childs[5^a]);
                    first_node_indx = next_Node(tx1,tyM,tz1,8,7,8);
                    //if(currNode!=null)
                    //    currNode = currNode.childs[first_node_indx];
                    break;
        case 6 :
                    proc_subtree(txM,tyM,tz0,tx1,ty1,tzM,node.childs[6^a]);
                    first_node_indx = next_Node(tx1,ty1,tzM,8,8,7);
                    //if(currNode!=null)
                    //    currNode = currNode.childs[first_node_indx];
                    break;
        case 7 :
                    proc_subtree(txM,tyM,tzM,tx1,ty1,tz1,node.childs[7^a]);
                    first_node_indx = 8;
                    //if(currNode!=null)
                    //    currNode = currNode.childs[7];
                    break;
        }
       //node = currNode;
       //if(currNode==null)
       //    break;
    } while (first_node_indx < 8);
     return;
}

//to delete all the scene node from the memory
function scene_octree_delete(node){

    if(node==null)
        return;
    if(node.isLeaf()){
        delete node.center;
        delete node.radius;
        node.center = null;
        node.radius = null;
        for(var i=0; i<node.spheres.length; i++)
            delete node.spheres[i];
        for(i=0; i<node.cylinders.length; i++)
            delete node.cylinders[i];
        for(i=0; i<node.triangles.length; i++)
            delete node.triangles[i];
        for(i=0; i<node.neighbours.length; i++)
            delete node.neighbours[i];
        delete node;
        node = null;
        return;
        //scene_octree_delete(curr_Node);
    }
    else if(!node.isLeaf()){
        for(var i=0; i<8; i++){
            if(node.childs[0]!=null){
                scene_octree_delete(node.childs[i]);
                node.childs[i] = null;
            }
        }
        node.leaf = true;
        scene_octree_delete(node);
    }
}

//delete all the pre allocated space for the ray traversal and leaf structures
function delete_all_arrays(){

    for(var i=0; i<allScene_Leafs.length; i++)
        delete allScene_Leafs[i];
    for(i=0; i<crossed_Octants.length; i++)
        delete crossed_Octants[i];
}


//accrding to the paper "Improved View Frustum Culling Technique for real time Virtual Heritage Application"
function inside_frustum(point, camera_pos, camera_frame, near, far, aspectratio, degree){

	var c_P = subPoints(point, camera_pos);
	var p_z = dotProduct(c_P, camera_frame[2]);
	if(p_z < near || p_z>far)
		return false;

	var p_y = dotProduct(c_P, camera_frame[1]);

	var h = p_z*2*Math.tan((Math.PI/180)*(degree/2.0));
	if(p_y<(-h/2.0) || p_y>(h/2.0))
		return false;

	var p_x = dotProduct(c_P, camera_frame[0]);
	var w = h*aspectratio;

	if(p_x<(-w/2.0) || p_x>(w/2.0))
		return false;
	return true;
}


function make_all_visible(node, visib){

	if(node==null)
		return;
	if(node.isLeaf()){
		if(visib==0)
			unvisible_list.push(node);
		else if(visib==2)
			visible_list.push(node);
		else if(visib==1)
			half_visible_list.push(node);
		node.visibility = visib;
		return;
	}
	else{
		node.visibility = visib;
                for(var i=0; i<8; i++)
                    make_all_visible(node.childs[i], visib);
		return;
	}
	return;
}


//first check if the six corners or center are inside the frustum,
//if all of them are inside the frustum then make it full visible,
//or some of the corners are inside the frustum, make it half visible, or make the node unvisible.
//if it node is complately unvisible check if the node is inside the node, then we have to check also its childerns separetly again.
//secondly check the



function find_visibility(node, camera_pos, camera_frame, frustum_near, frustum_far, aspectratio, degree){

	if(node==null)
		return;

    var corner_1 = [node.center[0]-node.radius[0], node.center[1]-node.radius[1], node.center[2]-node.radius[2]];
    var corner_2 = [node.center[0]+node.radius[0], node.center[1]-node.radius[1], node.center[2]-node.radius[2]];
    var corner_3 = [node.center[0]+node.radius[0], node.center[1]-node.radius[1], node.center[2]+node.radius[2]];
    var corner_4 = [node.center[0]-node.radius[0], node.center[1]-node.radius[1], node.center[2]+node.radius[2]];
    var corner_5 = [node.center[0]-node.radius[0], node.center[1]+node.radius[1], node.center[2]-node.radius[2]];
    var corner_6 = [node.center[0]+node.radius[0], node.center[1]+node.radius[1], node.center[2]-node.radius[2]];
    var corner_7 = [node.center[0]+node.radius[0], node.center[1]+node.radius[1], node.center[2]+node.radius[2]];
    var corner_8 = [node.center[0]-node.radius[0], node.center[1]+node.radius[1], node.center[2]+node.radius[2]];
    var corners  = [corner_1, corner_2, corner_3, corner_4, corner_5, corner_6, corner_7, corner_8];
    var count_inside = 0;

    for(var i=0; i<8; i++){
       if(inside_frustum(corners[i], camera_pos, camera_frame, frustum_near, frustum_far, aspectratio, degree))
          count_inside += 1;
    }

    if(count_inside==8){
   	   make_all_visible(node, 2);
   	   return;
    }
    else if(count_inside>0 && count_inside<8){

    	if(node.isLeaf()){
    		make_all_visible(node, 1);
    		return;
    	}
        for(var i=0; i<8; i++)
            find_visibility(node.childs[i], camera_pos, camera_frame, frustum_near, frustum_far, aspectratio, degree);
    	node.visibility = 1;
    	return;
    }
    else if(count_inside==0){ //check if it is inside, if it is inside continue with its children.
    	var outside = false;
    	if((node.center[0] - node.radius[0])>camera_pos[0] || (node.center[0] + node.radius[0])<camera_pos[0])
    		outside = true;
    	else if((node.center[0] - node.radius[0])>camera_pos[1] || (node.center[1] + node.radius[1])<camera_pos[1])
    		outside = true;
    	else if((node.center[2] - node.radius[2])>camera_pos[2] || (node.center[2] + node.radius[2])<camera_pos[2])
    		outside = true;
    	if(outside){
    		node.visibility = 0;
    		make_all_visible(node, 0);
    		return;
    	}
    	else{
    		for(var i=0; i<8; i++)
                    find_visibility(node.childs[i], camera_pos, camera_frame, frustum_near, frustum_far, aspectratio, degree);
    		return;
    	}
    }
    if(node.isLeaf())
    	return;

    return;
}


//var visible_list      = [];
//var unvisible_list    = [];
//var half_visible_list = [];

function find_radius_center(obj, type, center, radius){

	if(type==0){ //sphere BBox
		center.point = obj.protein_obj.atoms[obj.atom_ID-1].getCoord();
		var rad = Math.sqrt(obj.radius*obj.radius*2);
		radius.point = [rad, rad, rad];
	}
	else if(type==1){ //cylinder BBox
		var center_1;
                var center_2;
                if(obj.mode!=6){
                    center_1 = obj.protein_obj.atoms[obj.atom_IDS[0]-1].getCoord();
                    center_2 = obj.protein_obj.atoms[obj.atom_IDS[1]-1].getCoord();
                }
                else{
                    center_1 = obj.pos_1;
                    center_2 = obj.pos_2;
                }

                var rad;
		if(obj.mode==1)
			rad = 0.3;
		else if(obj.mode!=1 && obj.mode!=6)
			rad = 0.2;
                else if(obj.mode==6)
                    rad = obj.radius;
		center.point = addPoints(center_1, center_2);
		center.point = multiplyPnt(center.point, 0.5);
		var diag = [center_1[0] - center_2[0], center_1[1] - center_2[1], center_1[2] - center_2[2]];
		diag = [Math.abs(diag[0]), Math.abs(diag[1]), Math.abs(diag[2])];
		diag = multiplyPnt(diag, 0.5);
		diag = [diag[0] + rad, diag[1] + rad, diag[2] + rad];
		radius.point = diag;
	}
}



function test_element(obj, camera_pos, camera_frame, frustum_near, frustum_far, aspectratio, degree, type){

    if(obj==null)
            return;
    //var inter_pnt = new intersection_pnt();
    var c = new intersection_pnt();
    var r = new intersection_pnt();
    find_radius_center(obj, type, c, r);

    var center = [c.point[0],c.point[1], c.point[2]];
    var radius = [r.point[0],r.point[1], r.point[2]];
    delete c;
    delete r;

    var corner_1 = [center[0]-radius[0], center[1]-radius[1], center[2]-radius[2]];
    var corner_2 = [center[0]+radius[0], center[1]-radius[1], center[2]-radius[2]];
    var corner_3 = [center[0]+radius[0], center[1]-radius[1], center[2]+radius[2]];
    var corner_4 = [center[0]-radius[0], center[1]-radius[1], center[2]+radius[2]];
    var corner_5 = [center[0]-radius[0], center[1]+radius[1], center[2]-radius[2]];
    var corner_6 = [center[0]+radius[0], center[1]+radius[1], center[2]-radius[2]];
    var corner_7 = [center[0]+radius[0], center[1]+radius[1], center[2]+radius[2]];
    var corner_8 = [center[0]-radius[0], center[1]+radius[1], center[2]+radius[2]];
    var corners = [corner_1, corner_2, corner_3, corner_4, corner_5, corner_6, corner_7, corner_8];
    var count_inside = 0;

    for(var i=0; i<8; i++){
       if(inside_frustum(corners[i], camera_pos, camera_frame, frustum_near, frustum_far, aspectratio, degree))
          count_inside += 1;
    }

    if(count_inside>0)
    	return true;

    return false;
}


function fill_visible_lists(node){

	if(node==null)
		return;
	else if(node.isLeaf()){
		if(node.spheres.length==0 && node.cylinders.length==0 && node.triangles.length==0)
			return;
		else if(node.visibility==0)
			unvisible_list.push(node);
		else if(node.visibility==1)
			half_visible_list.push(node);
		else if(node.visibility==2)
			visible_list.push(node);
	}
	else{
		for(var i=0; i<8; i++)
                    fill_visible_lists(node.childs[i]);
	}
	return;
}


function traverse_vis_lists(node, cam_pos, cam_frame, near, far, ratio, view_angle){

	var prev_un_vis_length   = unvisible_list.length;
	var prev_vis_length      = visible_list.length;
	var prev_half_vis_length = half_visible_list.length;

	var prev_u = unvisible_list;
	var prev_h = half_visible_list;
	var prev_v = visible_list;

	unvisible_list = [];
	half_visible_list = [];
	visible_list   = [];

	find_visibility(node, cam_pos, cam_frame, near, far, ratio, view_angle);
	//fill_visible_lists(node);

	var diff_1 = Math.abs(prev_un_vis_length - unvisible_list.length);
	var diff_2 = Math.abs(prev_vis_length - visible_list.length);
	var diff_3 = Math.abs(prev_half_vis_length - half_visible_list.length);

	var length_un = unvisible_list.length < 9 ? 10 : unvisible_list.length;
	var length_vi = visible_list.length < 9 ? 10 : visible_list.length;
	var length_ha = half_visible_list.length < 9 ? 10 : half_visible_list.length;
	if(diff_1/length_un<0.2 && diff_2/length_vi<0.2 && diff_3/length_ha<0.2){
		unvisible_list = prev_u;
		half_visible_list = prev_h;
		visible_list   = prev_v;
		return;
	}
	make_list_unvisited();

	for(var i=0; i<unvisible_list.length;i++){

		var sphere_list = unvisible_list[i].spheres;
		for(var j=0; j<sphere_list.length; j++){
			if(sphere_list[j]==null)
				continue;
                        if(sphere_list[j].sphere_translate==null)
                            continue;
			sphere_list[j].sphere_translate.visible = false;
		}
		var cylinder_list = unvisible_list[i].cylinders;
		for(var j=0; j<cylinder_list.length; j++){
			if(cylinder_list[j]==null)
				continue;
                        else if(cylinder_list[j].cylinder_trans==null)
                            continue;
			cylinder_list[j].cylinder_trans.visible = false;
		}
	}

	for(var i=0; i<visible_list.length; i++){

		var sphere_list = visible_list[i].spheres;
		for(var j=0; j<sphere_list.length; j++){
			if(sphere_list[j]==null)
				continue;
			if(sphere_list[j].already_visited)
				continue;
                        if(sphere_list[j].sphere_translate==null)
                            continue;
			if(sphere_list[j].mode==0 && sphere_list[j].protein_obj.protein_render_mode==0){
				sphere_list[j].sphere_translate.visible = true;
			}
			else if(sphere_list[j].mode==1 && sphere_list[j].protein_obj.protein_render_mode==1){
				sphere_list[j].sphere_translate.visible = true;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==2 || sphere_list[j].protein_obj.protein_render_mode==7){
				if(sphere_list[j].mode==2 || sphere_list[j].mode==3 || sphere_list[j].mode==4 || sphere_list[j].mode==5)
					sphere_list[j].sphere_translate.visible = true;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==3 && sphere_list[j].mode==3){
				sphere_list[j].sphere_translate.visible = true;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==4){
				if(sphere_list[j].mode==3 || sphere_list[j].mode==4)
					sphere_list[j].sphere_translate.visible = true;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==5){
				if(sphere_list[j].mode==3 || sphere_list[j].mode==4 || sphere_list[j].mode==5)
					sphere_list[j].sphere_translate.visible = true;
			}
			sphere_list[j].already_visited = true;
		}
		var cylinder_list = visible_list[i].cylinders;
		for(var j=0; j<cylinder_list.length; j++){
			if(cylinder_list[j]==null)
				continue;
			if(cylinder_list[j].cylinder_trans==null)
                            continue;
                        if(cylinder_list[j].already_visited)
				continue;
			if(cylinder_list[j].protein_obj.protein_render_mode==0)
				continue;
			else if(cylinder_list[j].protein_obj.protein_render_mode==1 || cylinder_list[j].protein_obj.protein_render_mode==2){
				if(cylinder_list[j].mode==3 || cylinder_list[j].mode==6)
					continue;
				cylinder_list[j].cylinder_trans.visible = true;
			}
			else if(cylinder_list[j].protein_obj.protein_render_mode==3 && cylinder_list[j].mode==3){
				cylinder_list[j].cylinder_trans.visible = true;
			}
			else if(cylinder_list[j].protein_obj.protein_render_mode==4 && cylinder_list[j].mode==4){
				cylinder_list[j].cylinder_trans.visible = true;
			}
			else if(cylinder_list[j].protein_obj.protein_render_mode==5){
				if(cylinder_list[j].mode==4 || cylinder_list[j].mode==5)
					cylinder_list[j].cylinder_trans.visible = true;
			}
                        else if(cylinder_list[j].protein_obj.protein_render_mode==6){
                            if(cylinder_list[j].mode==6)
					cylinder_list[j].cylinder_trans.visible = true;
                        }
			cylinder_list[j].already_visited = true;
		}
	}

	for(var i=0; i<half_visible_list.length; i++){

		var sphere_list = half_visible_list[i].spheres;
		for(var j=0; j<sphere_list.length; j++){
			if(sphere_list[j]==null)
				continue;
                        if(sphere_list[j].sphere_translate==null)
                            continue;
			if(sphere_list[j].already_visited)
				continue;
			if(sphere_list[j].mode==0 && sphere_list[j].protein_obj.protein_render_mode==0){
				if(test_element(sphere_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 0))
					sphere_list[j].sphere_translate.visible = true;
				else
					continue;
			}
			else if(sphere_list[j].mode==1 && sphere_list[j].protein_obj.protein_render_mode==1){
				if(test_element(sphere_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 0))
					sphere_list[j].sphere_translate.visible = true;
				else
					continue;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==2){
				if(sphere_list[j].mode==2 || sphere_list[j].mode==3 || sphere_list[j].mode==4 || sphere_list[j].mode==5)
					if(test_element(sphere_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 0))
						sphere_list[j].sphere_translate.visible = true;
					else
						continue;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==3 && sphere_list[j].mode==3){
				if(test_element(sphere_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 0))
					sphere_list[j].sphere_translate.visible = true;
				else
					continue;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==4){
				if(sphere_list[j].mode==3 || sphere_list[j].mode==4)
					if(test_element(sphere_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 0))
						sphere_list[j].sphere_translate.visible = true;
					else
						continue;
			}
			else if(sphere_list[j].protein_obj.protein_render_mode==5){
				if(sphere_list[j].mode==3 || sphere_list[j].mode==4 || sphere_list[j].mode==5)
					if(test_element(sphere_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 0))
						sphere_list[j].sphere_translate.visible = true;
					else
						continue;
			}
			sphere_list[j].alreay_visited = true;
		}
		var cylinder_list = half_visible_list[i].cylinders;
		for(var j=0; j<cylinder_list.length; j++){
			if(cylinder_list[j]==null)
				continue;
                        if(cylinder_list[j].cylinder_trans==null)
                            continue;
			if(cylinder_list[j].already_visited)
				continue;
			if(cylinder_list[j].protein_obj.protein_render_mode==0)
				continue;
			else if(cylinder_list[j].protein_obj.protein_render_mode==1 || cylinder_list[j].protein_obj.protein_render_mode==2){
				if(cylinder_list[j].mode==3 || cylinder_list[j].mode==6)
					continue;
				if(test_element(cylinder_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 1))
					cylinder_list[j].cylinder_trans.visible = true;
				else
					continue;
			}
			else if(cylinder_list[j].protein_obj.protein_render_mode==3 && cylinder_list[j].mode==3){
				if(test_element(cylinder_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 1))
					cylinder_list[j].cylinder_trans.visible = true;
				else
					continue;
			}
			else if(cylinder_list[j].protein_obj.protein_render_mode==4 && cylinder_list[j].mode==4){
				if(test_element(cylinder_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 1))
					cylinder_list[j].cylinder_trans.visible = true;
				else
					continue;
			}
			else if(cylinder_list[j].protein_obj.protein_render_mode==5){
				if(cylinder_list[j].mode==4 || cylinder_list[j].mode==5)
					if(test_element(cylinder_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 1))
						cylinder_list[j].cylinder_trans.visible = true;
					else
						continue;
			}
                        else if(cylinder_list[j].protein_obj.protein_render_mode==6){
                            if(cylinder_list[j].mode==6)
                                if(test_element(cylinder_list[j], cam_pos, cam_frame, near, far, ratio, view_angle, 1))
                                  cylinder_list[j].cylinder_trans.visible = true;
			    else
				continue;

                        }
			cylinder_list[j].already_visited = true;
		}
	}

}

function make_list_unvisited(){

	for(var i=0; i<unvisible_list.length; i++){
		var obj_list = unvisible_list[i].spheres;
		for(var j=0; j<obj_list.length; j++){
			if(obj_list[j]==null)
				continue;
			obj_list[j].already_visited = false;
		}
		obj_list = unvisible_list[i].cylinders;
		for(var j=0; j<obj_list.length; j++){
			if(obj_list[j]==null)
				continue;
			obj_list[j].already_visited = false;
		}
	}

	for(var i=0; i<half_visible_list.length; i++){
		var obj_list = half_visible_list[i].spheres;
		for(var j=0; j<obj_list.length; j++){
			if(obj_list[j]==null)
				continue;
			obj_list[j].already_visited = false;
		}
		obj_list = half_visible_list[i].cylinders;
		for(var j=0; j<obj_list.length; j++){
			if(obj_list[j]==null)
				continue;
			obj_list[j].already_visited = false;
		}
	}

	for(var i=0; i<visible_list.length; i++){
		var obj_list = visible_list[i].spheres;
		for(var j=0; j<obj_list.length; j++){
			if(obj_list[j]==null)
				continue;
			obj_list[j].already_visited = false;
		}
		obj_list = visible_list[i].cylinders;
		for(var j=0; j<obj_list.length; j++){
			if(obj_list[j]==null)
				continue;
			obj_list[j].already_visited = false;
		}
	}
}