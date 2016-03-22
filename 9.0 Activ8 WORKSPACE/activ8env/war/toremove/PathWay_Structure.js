
protein_Network_Configuration = [];
protein_PathWay_Elements      = [];

function pathWay_Structure(){

    this.filepath = "";
    this.xml_Object = null;
    this.ontology_Labels  = [];
    this.molecule_List    = [];
    this.Interaction_List = [];
    this.pathway_list     = [];

    this.setFilePath = function(path){
        this.filepath = path;
    }
    this.getPath = function(){
        return this.filepath;
    }
    this.get_XMLObject = function(){
        return this.xml_Object;
    }
    this.set_XML_Object = function(data){
        return this.xml_Object;
    }
    this.get_Ontology = function(){
        return this.ontology_Labels;
    }
    
    this.getMolecule_List = function(){
        return this.molecule_List;
    }

    this.add_ontology_Label = function(new_label){
        this.ontology_Labels.push(new_label);
    }

    this.add_molecule = function(mol){
        this.molecule_List.push(mol);
    }

}

function ontology_Label(){
   this.name = "";
   this.ID   = -1;
   this.labelValues= [];

   this.setName = function(new_name){
       this.name = new_name;
   }
   this.getName = function(){
       return this.name;
   }
   this.setID = function(new_ID){
       this.ID = new_ID;
   }
   this.getID = function(){
       return this.ID;
   }
   this.newLabelValue = function( value_name, value_id, value_parent_id, value_GO ){

       var new_Label_Val = new Lavel_Value();
       new_Label_Val.setName(value_name);
       new_Label_Val.setID(value_id);
       new_Label_Val.setParentID(value_parent_id);
       new_Label_Val.setGo(value_GO);
       this.labelValues.push(new_Label_Val);
   }
   this.getLabel_Value = function(id){
       for(var i=0; i<this.labelValues.length; i++){
           if(this.labelValues[i].getID()==id)
               return this.labelValues[i];
       }
       return null;
   }
   this.getLabel_Value_List = function(){
       return this.labelValues;
   }
   this.getLabel_Val = function(index){ //returns the label_Value object in the list
       if(index>=this.labelValues.length)
           return null;
       return this.labelValues[index];
   }   
}



function Label_Value(){

    this.name = "";
    this.ID   = -1;
    this.parent_ID = -1;
    this.GO        = "";

    this.getName = function(){
        return this.name;
    }
    this.setName = function(new_name){
        this.name = new_name;
    }
    this.setID = function(new_ID){
        this.ID = new_ID;
    }
    this.getID = function(){
        return this.ID;
    }
    this.getParentID = function(){
        return this.parentID;
    }
    this.setParentID = function(new_Parent_ID){
        this.parent_ID = new_Parent_ID;
    }
    this.setGo = function(new_Go){
        this.Go = new_Go;
    }
    this.getGo = function(){
        return this.Go;
    }
}


function molecule(){

    this.type = "";
    this.ID = "";
    this.whole_molecule_idref = "";
    this.part_molecule_idref  = "";

    this.names = [];
    this.FamilyMemberList = [];
    this.ComplexComponentList = [];

    this.setWholeID = function(id){
        this.whole_molecule_idref = id;
    }
    this.getWholeID = function(){
        return this.whole_molecule_idref;
    }
    this.setPartID = function(id){
        this.part_molecule_idref = id;
    }
    this.getPartID = function(){
       return this.part_molecule_idref;
    }

    this.setType = function(type){
        this.type = type;
    }
    this.getType = function(){
        return this.type;
    }
    this.setID = function(n){
        this.ID = n;
    }
    this.getID = function(){
        return this.ID;
    }

    this.addFamilyMember = function(id){
        this.FamilyMemberList.push(id);
    }
    this.addComplexComponentList = function(id){
        this.ComplexComponentList.push(id);
    }
}

function molecule_name(){
   this.name_type = "";
   this.long_name_type = "";
   this.value   = "";

   this.setName = function(n){
       this.name_type = n;
   }
   this.getName = function(){
       return this.name_type;
   }
   this.setLongName = function(n){
       this.long_name_type = n;
   }
   this.getLongName = function(){
       return this.long_name_type;
   }
   this.setValue = function(val){
       this.value = val;
   }
   this.getValue = function(){
       return this.value;
   }
}


function interaction(){
   
   this.type = "";
   this.ID   = "";
   this.source = null;
   this.components = [];
   
   this.setType = function(type){
       this.type = type;
   }
   this.getType = function(){
       return this.type;
   }
   this.setID = function(id){
       this.ID = id;
   }
   this.getID = function(){
       return this.ID;
   }
   
}

function interaction_Source(){

   this.ID = "";
   this.Text = "";

   this.setID = function(id){
        this.ID = id;
   }
   this.getID = function(){
       return this.ID;
   }
   this.setText = function(text){
       this.text = text;
   }
   this.getText = function(){
       return this.text;
   }
}

function interaction_component(){
   this.role_type      = "";
   this.molecule_idref = "";
   this.labels = [];

   this.setType = function(type){
       this.role_type = type;
   }
   this.getType = function(){
       return this.role_type;
   }
   this.setID = function(id){
       this.molecule_idref = id;
   }
   this.getID = function(){
       return this.molecule_idref;
   }
}


function interaction_component_label(){
   this.label_type = "";
   this.value      = "";

   this.setType = function(type){
       this.type = type;
   }
   this.getType = function(){
       return this.type;
   }
   this.setValue = function(val){
       this.value = val;
   }
   this.getValue = function(val){
       return this.value;
   }
}

function pathway_element(){

   this.ID = -1;
   this.subnet = false;
   this.organism = "";
   this.longname = "";
   this.shortname= "";
   this.source   = null; //use interaction source structure for this because it is same
   this.componentList = [];  //holds the interaction_idref

   this.setID = function(id){
       this.ID = id;
   }
   this.getID = function(){
    return this.ID;
   }
   this.setLongName = function(name){
       this.longname = name;
   }
   this.getLongName = function(){
       return this.longname;
   }
   this.setShortName = function(name){
       this.shortname = name;
   }
   this.getShortName = function(name){
       this.shortname = name;
   }
   this.setSubnet = function(val){
        this.subnet = val;
    }
   this.getSubnet = function(){
       return this.subnet;
   }
   this.setOrganism = function(val){
       this.organism = val;
   }
   this.getOrganism = function(){
       return this.organism;
   }
}




function readOntology(new_pathway, ontology_node){

    for(var i=0; i<ontology_node.childNodes.length; i++){
        //create new label now
        if(ontology_node.childNodes[i].tagName=="LabelType"){
            //and read the childs of this label
            var new_Ontology_Label = new ontology_Label();
            new_Ontology_Label.setName(ontology_node.childNodes[i].attributes.getNamedItem("name").nodeValue);
            new_Ontology_Label.setID(ontology_node.childNodes[i].attributes.getNamedItem("name").nodeValue);
            new_pathway.ontology_Labels.push(new_Ontology_Label);

            for(var k=0; k<ontology_node.childNodes[i].childNodes.length; k++){

                var curr_labelType_Child = ontology_node.childNodes[i].childNodes[k];
                if(curr_labelType_Child.tagName=="LabelValueList"){

                    for(var t=0; t<curr_labelType_Child.childNodes.length; t++){
                        var curr_child = curr_labelType_Child.childNodes[t];
                        //create new Value

                        if(curr_child.tagName=="LabelValue"){

                            var new_label_value = new Label_Value();
                            new_label_value.setName(curr_child.attributes.getNamedItem("name").nodeValue);
                            new_label_value.setID(curr_child.attributes.getNamedItem("id").nodeValue);
                            new_label_value.setParentID(curr_child.attributes.getNamedItem("parent_idref").nodeValue);
                            new_Ontology_Label.labelValues.push(new_label_value);
                        }

                    }


                }

            }
        }
    }

}

function readModel(new_pathway, model_node){

     for(var i=0; i<model_node.childNodes.length; i++){

        if(model_node.childNodes[i].tagName=="MoleculeList"){

            for(var j=0; j<model_node.childNodes[i].childNodes.length; j++){
                var curr_Molecule_Node = model_node.childNodes[i].childNodes[j];

                if(curr_Molecule_Node.tagName=="Molecule"){
                    var new_Molecule = new molecule();
                    new_Molecule.setType(curr_Molecule_Node.attributes.getNamedItem("molecule_type").nodeValue);
                    new_Molecule.setID(curr_Molecule_Node.attributes.getNamedItem("id").nodeValue);
                    for(var m=0; m<curr_Molecule_Node.childNodes.length; m++){
                        var molecule_element = curr_Molecule_Node.childNodes[m];
                        if(molecule_element.tagName=="Name"){
                            var new_mol_name = new molecule_name();
                            new_mol_name.setName(molecule_element.attributes.getNamedItem("name_type").nodeValue);
                            new_mol_name.setLongName(molecule_element.attributes.getNamedItem("long_name_type").nodeValue);
                            new_mol_name.setValue(molecule_element.attributes.getNamedItem("value").nodeValue);
                            new_Molecule.names.push(new_mol_name);
                        }
                        else if(molecule_element.tagName=="FamilyMemberList"){
                            for(var t=0; t<molecule_element.childNodes.length; t++){
                                if(molecule_element.childNodes[t].tagName=="Member")
                                    new_Molecule.FamilyMemberList.push( molecule_element.childNodes[t].attributes.getNamedItem("member_molecule_idref").nodeValue);
                            }
                        }
                        else if(molecule_element.tagName=="Part"){

                          new_Molecule.setWholeID(molecule_element.attributes.getNamedItem("whole_molecule_idref"));
                          new_Molecule.setPartID(molecule_element.attributes.getNamedItem("part_molecule_idref"));

                        }

                    }
                  new_pathway.molecule_List.push(new_Molecule);
                }

            }

        }
        else if(model_node.childNodes[i].tagName=="InteractionList"){

           for(var m=0; m<model_node.childNodes[i].childNodes.length; m++){
               var curr_Interaction_Node = model_node.childNodes[i].childNodes[m];

              if(curr_Interaction_Node.tagName=="Interaction"){

                 var new_Interaction = new interaction();
                 new_Interaction.setType(curr_Interaction_Node.attributes.getNamedItem("interaction_type").nodeValue);
                 new_Interaction.setID(curr_Interaction_Node.attributes.getNamedItem("id").nodeValue);
                 new_pathway.Interaction_List.push(new_Interaction);

                 for(var t=0; t<curr_Interaction_Node.childNodes.length; t++){

                     if(curr_Interaction_Node.childNodes[t].tagName=="Source"){

                        var sourceNode = curr_Interaction_Node.childNodes[t];
                        var new_source = new interaction_Source();
                        new_source.setID(sourceNode.attributes.getNamedItem("id").nodeValue);
                        new_source.setText(sourceNode.textContent);
                        new_Interaction.source = sourceNode;
                     }
                     else if(curr_Interaction_Node.childNodes[t].tagName=="InteractionComponentList"){

                        for(var k=0; k<curr_Interaction_Node.childNodes[t].childNodes.length; k++){

                            var curr_component = curr_Interaction_Node.childNodes[t].childNodes[k];
                            if(curr_component.tagName=="InteractionComponent"){

                                var new_component = new interaction_component();
                                new_component.setType(curr_component.attributes.getNamedItem("role_type").nodeValue);
                                new_component.setID(curr_component.attributes.getNamedItem("molecule_idref").nodeValue);
                                new_Interaction.components.push(new_component);

                                for(var r = 0; r<curr_component.childNodes.length; r++){
                                    if(curr_component.childNodes[r].tagName=="Label"){

                                        var new_label = new interaction_component_label();
                                        new_label.setType( curr_component.childNodes[r].attributes.getNamedItem("label_type").nodeValue);
                                        new_label.setValue( curr_component.childNodes[r].attributes.getNamedItem("value").nodeValue);
                                        new_component.labels.push(new_label);
                                    }
                                }

                            }
                        }
                     }
                 }
             }
           }
        }
        else if(model_node.childNodes[i].tagName=="PathwayList"){

            for(var m=0; m<model_node.childNodes[i].childNodes.length; m++){

                var curr_Path = model_node.childNodes[i].childNodes[m];
                if(curr_Path.tagName=="Pathway"){

                    var new_path = new pathway_element();
                    new_path.setID(curr_Path.attributes.getNamedItem("id").nodeValue);
                    new_path.setSubnet(curr_Path.attributes.getNamedItem("subnet").nodeValue);
                    new_pathway.pathway_list.push(curr_Path);
                    for(var k=0; k<curr_Path.childNodes.length; k++){

                        if(curr_Path.childNodes[k].tagName=="Organism"){
                            new_path.setOrganism(curr_Path.childNodes[k].textContent);
                        }
                        else if(curr_Path.childNodes[k].tagName=="LongName"){
                           new_path.setLongName(curr_Path.childNodes[k].textContent);
                        }
                        else if(curr_Path.childNodes[k].tagName=="ShortName"){
                             new_path.setShortName(curr_Path.childNodes[k].textContent);
                        }

                        else if(curr_Path.childNodes[k].tagName=="Source"){

                            var source_path = new interaction_Source();
                            source_path.setID(curr_Path.childNodes[k].attributes.getNamedItem("id").nodeValue);
                            source_path.setText(curr_Path.childNodes[k].textContent);
                            new_path.source = source_path;
                        }
                        else if(curr_Path.childNodes[k].tagName=="PathwayComponentList"){
                            for(var r=0; r<curr_Path.childNodes[k].childNodes.length; r++){
                                var curr_component = curr_Path.childNodes[k].childNodes[r];
                                if(curr_component.tagName=="PathwayComponent"){
                                   new_path.componentList.push(curr_component.attributes.getNamedItem("interaction_idref").nodeValue);
                                }
                            }
                        }
                    }
                }
            }
        }
     }    
}


function parse_XML_File(file_object){
     
     var new_pathway = new pathWay_Structure();
     new_pathway.filepath = file_object.documentURI;
     new_pathway.xml_Object = file_object;

    for(var i=0; i<file_object.childNodes.length; i++ ){
        
        var curr_child = file_object.childNodes[i];

        for(var j=0; j<curr_child.childNodes.length; j++){

            var curr_node = curr_child.childNodes[j];
            if(curr_node.tagName=="Ontology"){
                readOntology(new_pathway, curr_node);
            }
            else if(curr_node.tagName=="Model"){
                readModel(new_pathway, curr_node);
            }
        }
    }
}




function createNetwork(){

}


function create_PathWay_Pyramid(level, pos){

   if(level>4)
       return;
    var new_pos = [pos[0], pos[1], pos[2]];
    for(var i=0; i<4; i++){

        var new_pos = [pos[0], pos[1]-4, pos[2]];
        if(i==0){
            new_pos[0] = pos[0] - 3;
            new_pos[2] = pos[2] - 3;
        }
        else if(i==1){
            new_pos[0] = pos[0] + 3;
            new_pos[2] = pos[2] - 3;
        }
        else if(i==2){
            new_pos[0] = pos[0] + 3;
            new_pos[2] = pos[2] + 3;
        }
        else if(i==3){
            new_pos[0] = pos[0] - 3;
            new_pos[2] = pos[2] + 3;
        }
        var transform;
        transform = g_pack.createObject('Transform');
        transform.parent = g_client.root;
        transform.translate(pos[0], pos[1], pos[2]);
        transform.cull      = false;
        transform.visible   = true;
        transform.addShape(n_Shape);

        if(level==3)
            return;
         var indexes = [0,1];
         var vertices  = [];
         vertices.push(pos[0]);
         vertices.push(pos[1]);
         vertices.push(pos[2]);
         vertices.push(new_pos[0]);
         vertices.push(new_pos[1]);
         vertices.push(new_pos[2]);

         var transform_line = g_pack.createObject('Transform');
         transform_line.parent = g_client.root;
         var shape = o3djs.debug.createLineShape(g_pack,
                                          o3djs.material.createConstantMaterial(g_pack,
                                                            g_colorViewInfo, [0, 1, 0, 1]),
                                                            vertices, indexes);
        transform_line.addShape(shape);
        transform_line.cull      = false;
        transform_line.visible   = true;
        create_PathWay_Pyramid(level+1, new_pos);
    }
}

