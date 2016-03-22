/* 
 * FPS Camera
 */

function FPSCamera(fov, width, height, near, far)
{
    this.fov = fov;
    this.width = width;
    this.height = height;
    this.aspect = width / height;
    this.near = near;
    this.far = far;
    this.target =[0,0,1];

    //this.math = o3djs.math;

    //create the perspective matrix
    g_colorViewInfo.drawContext.projection = g_math.matrix4.perspective(o3djs.math.degToRad(fov),
      width /height, // Aspect ratio.
      near,                   // Near plane.
      far);               // Far plane.
    //this.proj = o3djs.math.matrix4.perspective(o3djs.math.degToRad(fov), this.aspect, near, far);

    //create a standard view matrix
    this.view = o3djs.math.matrix4.lookAt([0, 0, 0],  // eye
                                         [0, 0, 1 ],  // target
                                         [0, 1, 0 ]); // up

    this.position = [1, 1, 1];
    this.look  = [0, 0, 0];
    this.right = [1, 0, 0];
    this.up    = [0, 1, 0];

    //define private functions
    this.normalize = function()
    {
        this.look = o3djs.math.normalize(this.look);

        this.right = o3djs.math.normalize(o3djs.math.cross(this.up, this.look));
        this.up = o3djs.math.normalize(o3djs.math.cross(this.look, this.right));
    }
}

//Creates a new view matrix
FPSCamera.prototype.LookAt = function(pos, target, up)
{
    this.position = pos;
    
    this.view = o3djs.math.matrix4.lookAt(pos, target, up);

    //this.look = o3djs.math.subVector(, pos);

    this.look = target; //[1.0, 0.0, 0.0];
    this.normalize();

    this.Update();
}


FPSCamera.prototype.setCamera = function(pos, direction, up)
{
    this.position = pos;

    this.view = o3djs.math.matrix4.lookAt(pos, direction, up);

    this.look = direction;

    this.normalize();

    this.Update();
}



//Creates a new projection matrix with the new aspect ratio (width / height)
FPSCamera.prototype.ResizeViewport = function(width, height)
{
    this.width = width;
    this.height = height;
    this.aspect = width / height;
    
    this.proj = o3djs.math.matrix4.perspective(o3djs.math.degToRad(this.fov), this.aspect, this.near, this.far);
}

FPSCamera.prototype.Walk = function(value)
{
    //var old_pos_y = this.position[1];
    this.position = o3djs.math.addVector(this.position, o3djs.math.mulScalarVector(value, this.look));
    //this.position = [this.position[0], old_pos_y, this.position[2]];
}

FPSCamera.prototype.Strafe = function(value)
{
    var R = [this.right.x, 0.0, this.right.z];
    this.position = o3djs.math.addVector(this.position, o3djs.math.mulScalarVector(value, this.right));
	g_eyePosition = [Number(this.position), Number(this.position.y), Number(this.position.z)];
}

FPSCamera.prototype.Look = function(pitchAngle, yawAngle, rollAngle)
{    
    //pitch
    var rotMatrix = o3djs.math.matrix4.axisRotation(this.right, pitchAngle);
    this.look = o3djs.math.matrix4.transformDirection(rotMatrix, this.look)
    this.up = o3djs.math.matrix4.transformDirection(rotMatrix, this.up);

    //yaw
    rotMatrix = o3djs.math.matrix4.rotationY(yawAngle)
    this.right = o3djs.math.matrix4.transformDirection(rotMatrix, this.right);
    this.up = o3djs.math.matrix4.transformDirection(rotMatrix, this.up);
    this.look = o3djs.math.matrix4.transformDirection(rotMatrix, this.look);

    //TODO: roll

    this.normalize();
}

FPSCamera.prototype.Update = function()
{
    this.target = o3djs.math.addVector(this.position, this.look);
    this.view = o3djs.math.matrix4.lookAt(this.position, this.target, this.up);
}


