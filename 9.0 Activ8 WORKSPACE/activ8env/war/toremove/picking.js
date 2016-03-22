o3djs.require('o3djs.picking');


var g_flashTimer = 0;
var g_debugHelper;
var g_debugLineGroup;
var g_debugLine;
var g_selectedInfo = null;
var g_pickInfoElem;     //For object picking
var g_treeInfo;         // information about the transform graph.

function pick(e) {
  //alert("In Picking Function");
  var worldRay = o3djs.picking.clientPositionToWorldRay(e.x, e.y, g_colorViewInfo.drawContext, g_client.width, g_client.height);
  var near = worldRay.near;
  var far  = worldRay.far;
  
  //alert("E_X= " + e.x + " E_Y= " + e.y + "VIEW-DRAW_CONTEXT= " + g_viewInfo.drawContext + " WIDTH= " + g_client.width + "HEIGHT= " + g_client.height);
  //unSelectAll(); 
  //alert("World Ray" + worldRay);
  //alert(g_treeInfo);
  
  var pickInfo = g_treeInfo.pick(worldRay);
  
  if (pickInfo) {
    //select(pickInfo);
	g_pickInfoElem.innerHTML = pickInfo.shapeInfo.shape.name;
	
    /*var normal = o3djs.element.getNormalForTriangle(pickInfo.element, pickInfo.rayIntersectionInfo.primitiveIndex);

    // Convert the normal from local to world space.
    normal = g_math.matrix4.transformNormal(pickInfo.shapeInfo.parent.transform.worldMatrix, normal);

    // Remove the scale from the normal.
    normal = g_math.normalize(normal);

    // Get the world position of the collision.
    var worldPosition = pickInfo.worldIntersectionPosition;

    // Add the normal to it to get a point in space above it with some
    // multiplier to scale it.
    var normalSpot = g_math.addVector(worldPosition, g_math.mulVectorScalar(normal, NORMAL_SCALE_FACTOR));

    // Move our debug line to show the normal
    g_debugLine.setVisible(true);
    g_debugLine.setEndPoints(worldPosition, normalSpot);
	*/
	
  } 
  else {
    g_debugLine.setVisible(false);
    g_pickInfoElem.innerHTML = '--nothing--';
  }
  
}

function updateInfo() {
  //alert("Inside updateInfo");
  if (!g_treeInfo) {
	//alert("o3djs.picking.createTransformInfo(g_client.root,null);");
    g_treeInfo = o3djs.picking.createTransformInfo(g_client.root,null);
	//alert(g_treeInfo);
  }
  g_treeInfo.update();
}

function unSelectAll() {
  if (g_selectedInfo) {
    // Remove it from the transform of the selected object.
    g_selectedInfo.shapeInfo.parent.transform.removeShape(g_highlightShape);
    // Remove everything related to it.
    o3djs.shape.deleteDuplicateShape(g_highlightShape, g_pack);
    g_highlightShape = null;
    g_selectedInfo = null;
  }
}

function select(pickInfo) {
  unSelectAll();
  if (pickInfo) {
    g_selectedInfo = pickInfo;
    // make a copy of the selected shape so we can use it to highlight.
    g_highlightShape = o3djs.shape.duplicateShape(g_pack, g_selectedInfo.shapeInfo.shape, 'highlight_');
    // Set all of it's elements to use the highlight material.
    var elements = g_highlightShape.elements;
    for (var ee = 0; ee < elements.length; ee++) {
      elements[ee].material = g_highlightMaterial;
    }
    // Add it to the same transform
    g_selectedInfo.shapeInfo.parent.transform.addShape(g_highlightShape);
    g_flashTimer = 0.0;  // make it change color immediately.
  }
}



