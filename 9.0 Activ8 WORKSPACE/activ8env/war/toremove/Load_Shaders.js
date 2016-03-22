o3djs.require('o3djs.util');
o3djs.require('o3djs.rendergraph');
o3djs.require('o3djs.pack');
o3djs.require('o3djs.math');
o3djs.require('o3djs.camera');
o3djs.require('o3djs.effect');
o3djs.require('o3djs.loader');

var g_pack;
var g_shaderSelection = 0;
var g_effects = [];

var g_bumpTextureSampler;
var g_bumpBumpsSampler;
var g_colorRampSampler;
var g_currentTimeParam;

var g_shaders = [
  {file: 'diffuse',               name: 'Diffuse'},
  {file: 'checker',               name: 'Checker'},
  {file: 'bump',                  name: 'Bump'},
  {file: 'bump',                  name: 'Bump With Texture'},
  {file: 'texture-only',          name: 'Texture Only'},
  {file: 'texture-colormult',     name: 'Texture with Color Multiplier'},
  {file: 'normal',                name: 'Normal'},
  {file: 'solid-color',           name: 'Solid Color'},
  {file: 'vertex-color',          name: 'Vertex Color'},
  {file: 'phong-with-colormult',  name: 'Blinn-Phong with Color Multiplier'},
  {file: 'toon',                  name: 'Toon'}];
  
  
/**
 * Apply the desired shader to our scene.
 * @param {!o3d.Pack} pack Variable referring to the scene's pack.
 * @param {number} shaderNumber Index into g_effects of which shader to use.
 */
 
 function setParam(object, paramName, value) {
  var param = object.getParam(paramName);
  if (param) {
    param.value = value;
  }
}

function applyShader(pack, shaderNumber) {
  var materials = pack.getObjectsByClassName('o3d.Material');
  // Make the change to each material. For our teapot, there is only one
  // material.
  for (var m = 0; m < materials.length; m++) {
    var material = materials[m];
	g_effects[shaderNumber].createUniformParameters(material);
    material.effect = g_effects[shaderNumber];
	
    // Set our shader values
    var colorParamValue = [0.8, 0.8, 0.8, 1];
    var lightPosParamValue = [600, 600, 1000];
	 
    setParam(material, 'lightPos', lightPosParamValue);
    setParam(material, 'lightWorldPos', lightPosParamValue);	
	setParam(material, 'cameraEye',  [gCamera.position[0], gCamera.position[1], gCamera.position[2]]);
    setParam(material, 'color', colorParamValue);
    setParam(material, 'colorMult', [.75, .75, 75., 1]);

  	
    // only use the texture input addition to bump mapping if on selection 3
    setParam(material, 'useTexture', (g_shaderSelection == 3) ? 1 : 0);
	
    setParam(material, 'lightIntensity', [0.8, 0.8, 0.8, 1]);
    setParam(material, 'ambientIntensity', [0.2, 0.2, 0.2, 1]);
    setParam(material, 'emissive', [0, 0, 0, 1]);
    setParam(material, 'ambient', [1, 1, 1, 1]);
    setParam(material, 'diffuse', colorParamValue);
    setParam(material, 'specular', [0.5, 0.5, 0.5, 1]);
    setParam(material, 'shininess', 50);
	
    setParam(material, 'BumpSampler', g_bumpBumpsSampler);
    setParam(material, 'AmbientSampler', g_bumpTextureSampler);
    setParam(material, 'DiffuseSampler', g_bumpTextureSampler);
    setParam(material, 'texSampler0', g_bumpTextureSampler);
    setParam(material, 'colorRamp', g_colorRampSampler);
    
	
    //var timeParam = material.getParam('inputTime');
	
	//alert(timeParam);
    //if (timeParam) {
     // timeParam.bind(g_currentTimeParam);
   // }
  }
}


/**
 * Swaps which shader we are using and applies it.
 */
function changeShader() {
  var shaderNumber = document.getElementById("shaderSelect").value;
  g_shaderSelection = parseFloat(shaderNumber);
  applyShader(g_pack, g_shaderSelection);
  //alert("Shader Changed");
}


function putallShaders(){
	
  var paramObject = g_pack.createObject('ParamObject');
  g_currentTimeParam = paramObject.createParam('timeParam','ParamFloat');

	  // Load effects and fill out options.
  /*options = '<select id="shaderSelect" name="shaderSelect"' +
            ' onChange="changeShader()">'		
  for(var s = 0; s < g_shaders.length; s++) 
  {
    g_effects[s] = g_pack.createObject('Effect');	
    var shaderString = 'shaders/' + g_shaders[s].file + '.shader';	
	o3djs.effect.loadEffect(g_effects[s], shaderString);
    options += '<option value="' + s + '"' + (s == 0 ? ' selected' : '') + '>' + g_shaders[s].name + '</option>';
  }
  options += '</select>';
  document.getElementById('shaderDiv').innerHTML = options;*/
}

