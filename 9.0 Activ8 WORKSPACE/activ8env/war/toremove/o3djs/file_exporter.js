

var pdb_File_Path;
var points_coord     = [];
var points_Atom_Type = []; 

var fileContent='';
var theLocation='';

function readFileViaApplet(n) {
	
	//document.f1.t1.value='Reading in progress...';
	alert("PASS " + theLocation);
	document.ReadURL.readFile(theLocation);
	alert("PASS");
	setTimeout("showFileContent()",100);
}

function showFileContent() {
	if (document.ReadURL.finished==0) {
		setTimeout("showFileContent()",100);
		return;
	}
	fileContent=document.ReadURL.fileContent;
	document.form1.textarea1.value=fileContent;
} 

function popup() 
{
  alert("Hello World")
}


function putFileName(filename)
{
	pdb_File_Path = filename;
}

function readFile(filename)
{
	alert(filename);
	alert(""After Putting File Name);
	var oRequest = new XMLHttpRequest();
	oRequest.open("GET",filename,false);
    oRequest.setRequestHeader('User-Agent',navigator.userAgent);
	oRequest.send(null);
	var text = oRequest.responseText;
	//return text;	
}



function ShowAvailableDrives()
{
    document.write(GetDriveList());
}

function GetDriveList()
{
 var fso, s, n, e, x;
 fso = new ActiveXObject("Scripting.FileSystemObject");
 e = new Enumerator(fso.Drives);
 s = "";
 do
 {
   x = e.item();
   s = s + x.DriveLetter;
   s += ":-    ";
   if (x.DriveType == 3)     n = x.ShareName;
   else if (x.IsReady)     n = x.VolumeName;
   else                     n = "[Drive not ready]";
   s += n + "<br>";
   e.moveNext();
 }  while (!e.atEnd());
 
 return(s);
}

