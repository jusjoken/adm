/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.io.File;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 *
 * @author jusjoken
 */
public class MenuNode {
    public static DefaultMutableTreeNode Testing;

    private String Parent = "";
    private String Name = "";
    private String ButtonText = "";
    private String SubMenu = "";
    private String ActionAttribute = "";
    private String ActionType = "";
    private String BGImageFile = "";
    private String BGImageFilePath = "";
    private Boolean IsDefault = false;
    private Boolean IsActive = true;
    private Boolean HasSubMenu = false;
    private Integer SortKey = 0;
    private Integer Level = 0;
    public static Integer SortKeyCounter = 0;
    public static Map<String,MenuNode> MenuNodeList = new LinkedHashMap<String,MenuNode>();
    public static DefaultMutableTreeNode root;

    public MenuNode(String bName){
        //create a MenuItem with just default values
        this(util.TopMenu,bName,0,util.ButtonTextDefault,Boolean.FALSE,null,util.ActionTypeDefault,null,null,Boolean.FALSE,Boolean.TRUE);
    }
    
    public MenuNode(String bParent, String bName, Integer bSortKey, String bButtonText, Boolean bHasSubMenu, String bSubMenu, String bActionType, String bAction, String bBGImageFile, Boolean bIsDefault, Boolean bIsActive){
        Parent = bParent;
        Name = bName;
        ButtonText = bButtonText;
        SubMenu = bSubMenu;
        HasSubMenu = bHasSubMenu;
        ActionType = bActionType;
        ActionAttribute = bAction;
        //SetBGImageFileandPath(bBGImageFile);
        IsDefault = bIsDefault;
        IsActive = bIsActive;
        SortKey = bSortKey;
        MenuNodeList.put(Name, this);
    }
    
    @Override
    public String toString(){
        return Name;
    }

    public void setAction(String ActionAttribute) {
        this.ActionAttribute = ActionAttribute;
    }

    public void setActionType(String ActionType) {
        this.ActionType = ActionType;
    }

    public void setBGImageFile(String BGImageFile) {
        SetBGImageFileandPath(BGImageFile);
    }

    private void SetBGImageFileandPath(String bBGImageFile){
        //see if using a GlobalVariable from a Theme or a path to an image file
        if (bBGImageFile==null || bBGImageFile.equals("")){
            //System.out.println("ADM: SetBGImageFileandPath for '" + bBGImageFile + "' - null found");
            BGImageFile = bBGImageFile;
            BGImageFilePath = bBGImageFile;
        }else if (bBGImageFile.contains(File.separator)){
            //a path to the image file is being used
            //System.out.println("ADM: SetBGImageFileandPath for '" + bBGImageFile + "' - path found");
            BGImageFile = bBGImageFile;
            BGImageFilePath = bBGImageFile;
        }else{
            //expect a Global Variable from the theme
            //System.out.println("ADM: SetBGImageFileandPath for '" + bBGImageFile + "' - variable found");
            BGImageFile = bBGImageFile;
            BGImageFilePath = sagex.api.WidgetAPI.EvaluateExpression(util.GetMyUIContext(), bBGImageFile).toString();
        }
    }
    
    public void setButtonText(String ButtonText) {
        this.ButtonText = ButtonText;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setParent(String Parent) {
        this.Parent = Parent;
    }

    public void setSortKey(String SortKey) {
        Integer tSortKey = 0;
        try {
            tSortKey = Integer.valueOf(SortKey);
        } catch (NumberFormatException ex) {
            System.out.println("ADM: setSortKey: error converting '" + SortKey + "' " + util.class.getName() + ex);
            tSortKey = SortKeyCounter++;
        }
//        //check if this SortKey is in use - if so then insert it after
//        InsertSortKey(tSortKey);
        this.SortKey = tSortKey;
    }
    
    public void setSubMenu(String SubMenu) {
        this.SubMenu = SubMenu;
    }
    
    public void setHasSubMenu(Boolean HasSubMenu) {
        this.HasSubMenu = HasSubMenu;
    }

    public void setHasSubMenu(String HasSubMenu) {
        if ("true".equals(HasSubMenu)){
            this.HasSubMenu = true;
        }else{
            this.HasSubMenu = false;
        }
    }

    public void setIsDefault(Boolean IsDefault) {
        this.IsDefault = IsDefault;
    }

    public void setIsDefault(String IsDefault) {
        if ("true".equals(IsDefault)){
            this.IsDefault = true;
        }else{
            this.IsDefault = false;
        }
    }

    public void setIsActive(Boolean IsActive) {
        this.IsActive = IsActive;
    }

    public void setIsActive(String IsActive) {
        if ("true".equals(IsActive)){
            this.IsActive = true;
        }else{
            this.IsActive = false;
        }
    }


    public static void LoadMenuItemsFromSage(){
        //create and store the top menu node
        root = new DefaultMutableTreeNode(new MenuNode(util.TopMenu));
        
        String PropLocation = "";
        
        //find all MenuItem Name entries from the SageTV properties file
        String[] MenuItemNames = sagex.api.Configuration.GetSubpropertiesThatAreBranches(util.SagePropertyLocation);
        
        if (MenuItemNames.length>0){
            //clear the existing MenuItems from the list
            MenuNode.MenuNodeList.clear();
            
            //load MenuItems
            for (String tMenuItemName : MenuItemNames){
                PropLocation = util.SagePropertyLocation + tMenuItemName;
                MenuNode NewMenuItem = new MenuNode(tMenuItemName);
                NewMenuItem.setAction(sagex.api.Configuration.GetProperty(PropLocation + "/Action", null));
                NewMenuItem.setActionType(sagex.api.Configuration.GetProperty(PropLocation + "/ActionType", util.ActionTypeDefault));
                NewMenuItem.setBGImageFile(sagex.api.Configuration.GetProperty(PropLocation + "/BGImageFile", null));
                NewMenuItem.setButtonText(sagex.api.Configuration.GetProperty(PropLocation + "/ButtonText", util.ButtonTextDefault));
                NewMenuItem.setName(sagex.api.Configuration.GetProperty(PropLocation + "/Name", tMenuItemName));
                NewMenuItem.setParent(sagex.api.Configuration.GetProperty(PropLocation + "/Parent", "xTopMenu"));
                NewMenuItem.setSortKey(sagex.api.Configuration.GetProperty(PropLocation + "/SortKey", null));
                NewMenuItem.setSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/SubMenu", null));
                NewMenuItem.setHasSubMenu(sagex.api.Configuration.GetProperty(PropLocation + "/HasSubMenu", "false"));
                NewMenuItem.setIsDefault(sagex.api.Configuration.GetProperty(PropLocation + "/IsDefault", "false"));
                NewMenuItem.setIsActive(sagex.api.Configuration.GetProperty(PropLocation + "/IsActive", "true"));
                //System.out.println("ADM: LoadMenuItemsFromSage: loaded - '" + tMenuItemName + "'");
            }
            if (MenuNodeList.size()>0){
                //create the tree nodes
                for (MenuNode Node : MenuNodeList.values()){
                    //check if the current node exists yet
                    AddNode(Node);
                }
            }
            
        }else{
            //load a default Menu here.  Load a Diamond Menu if Diamond if active
            System.out.println("ADM: LoadMenuItemsFromSage: no MenuItems found - loading default menu.");
            //LoadMenuItemDefaults();
        }
        System.out.println("ADM: LoadMenuItemsFromSage: loaded " + MenuNodeList.size() + " MenuItems");
        
        //now that the menus are loaded - set a level for each menu item and store it
        //MenuItem.SetMenuItemLevels();
        //now ensure SortKeys are in order
        //FixSortOrder();
        
        return;
    }
    
    private static void AddNode(MenuNode aNode){
        if (!NodeExists(root, aNode.Name)){
            //check if the current nodes parent exists yet
            if (aNode.Parent.equals(util.TopMenu)){
                root.add(new DefaultMutableTreeNode(aNode));
                System.out.println("ADM: AddNode: node '" + aNode.ButtonText + "' not found so adding to ROOT");
            }else{
                AddNode(MenuNodeList.get(aNode.Parent));
                DefaultMutableTreeNode tParent = FindNode(root, aNode.Parent);
                tParent.add(new DefaultMutableTreeNode(aNode));
                System.out.println("ADM: AddNode: node '" + aNode.ButtonText + "' not found so adding");
            }
        }else{
            System.out.println("ADM: AddNode: node '" + aNode.ButtonText + "' already exists");
        }
    }
    
    private static void createNodes() {
        //DefaultMutableTreeNode grandparent;
        //DefaultMutableTreeNode parent;

        root = new DefaultMutableTreeNode(new MenuNode(util.TopMenu));

        DefaultMutableTreeNode grandparent = new DefaultMutableTreeNode(new MenuNode("Child1"));
        DefaultMutableTreeNode grandparent2 = new DefaultMutableTreeNode(new MenuNode("Child2"));
        root.add(grandparent);
        root.add(grandparent2);

        DefaultMutableTreeNode parent = new DefaultMutableTreeNode(new MenuNode("Child1 - Child1"));
        grandparent.add(parent);
        Testing = new DefaultMutableTreeNode(new MenuNode("Child1 - Child2"));
        grandparent.add(Testing);
        parent = new DefaultMutableTreeNode(new MenuNode("Child1 - Child3"));
        grandparent.add(parent);

    }

    public static void Test(){
        //createNodes();
        LoadMenuItemsFromSage();
        System.out.println("ADM: TEST NODES: ");
        ListChildren(root);
        
        //find by Node
//        FindNode(root, Testing) ;
//        DefaultMutableTreeNode Child3 = FindNode(root, "Child1 - Child3") ;
//
//        //move Child 3 up one
//        moveNode(Child3,-1);
//        ListChildren(root);
//        moveNode(Child3,-1);
//        ListChildren(root);
//        moveNode(Child3,-1);
//        ListChildren(root);
//
//        setParent(Child3, FindNode(root, "Child2"));
//        ListChildren(root);
    }
    
    public static void ListChildren(DefaultMutableTreeNode Node){
        Enumeration<DefaultMutableTreeNode> en = Node.preorderEnumeration();
        String tButtonText;
        while (en.hasMoreElements())   {
            DefaultMutableTreeNode child = en.nextElement();
            MenuNode tMenu = (MenuNode)child.getUserObject();
            tButtonText = tMenu.ButtonText;
            System.out.println("ADM: TEST NODES: Child = '" + child + "' childcount = '" + child.getChildCount() + "' Parent = '" + child.getParent() + "' Level = '" + child.getLevel() + "' Leaf = '" + child.isLeaf() + "' buttonText = '" + tButtonText + "' Path = '" + GetPath(child) + "'"  );
        }         
    }
    
    public static DefaultMutableTreeNode FindNode(DefaultMutableTreeNode Root, DefaultMutableTreeNode Node){
        Enumeration<DefaultMutableTreeNode> en = Root.preorderEnumeration();
        while (en.hasMoreElements())   {
            DefaultMutableTreeNode child = en.nextElement();
            if(child.equals(Node)) 
            { 
                //tree node with string found 
                //System.out.println("ADM: FindNode: '" + Node + "' found = '" + child + "' childcount = '" + child.getChildCount() + "' Parent = '" + child.getParent() + "' Level = '" + child.getLevel() + "' Leaf = '" + child.isLeaf() + "'"  );
                return child;                          
            } 
        }         
        //System.out.println("ADM: FindNode: '" + Node + "' not found.");
        return null;
    }
    
    public static DefaultMutableTreeNode FindNode(DefaultMutableTreeNode Root, String NodeKey){
        Enumeration<DefaultMutableTreeNode> en = Root.preorderEnumeration();
        while (en.hasMoreElements())   {
            DefaultMutableTreeNode child = en.nextElement();
            if(NodeKey.equals(child.getUserObject().toString())) 
            { 
                //tree node with string found 
                //System.out.println("ADM: FindNode: '" + NodeKey + "' found = '" + child + "' childcount = '" + child.getChildCount() + "' Parent = '" + child.getParent() + "' Level = '" + child.getLevel() + "' Leaf = '" + child.isLeaf() + "'"  );
                return child;                          
            } 
        }         
        //System.out.println("ADM: FindNode: '" + NodeKey + "' not found.");
        return null;
    }
    
    public static Boolean NodeExists(DefaultMutableTreeNode Root, String NodeKey){
        Enumeration<DefaultMutableTreeNode> en = Root.preorderEnumeration();
        while (en.hasMoreElements())   {
            DefaultMutableTreeNode child = en.nextElement();
            if(NodeKey.equals(child.getUserObject().toString())) 
            { 
                //tree node with string found 
                //System.out.println("ADM: NodeExists: '" + NodeKey + "' found = '" + child + "' childcount = '" + child.getChildCount() + "' Parent = '" + child.getParent() + "' Level = '" + child.getLevel() + "' Leaf = '" + child.isLeaf() + "'"  );
                return Boolean.TRUE;                          
            } 
        }         
        //System.out.println("ADM: NodeExists: '" + NodeKey + "' not found.");
        return Boolean.FALSE;
    }
    
    public static String GetPath(DefaultMutableTreeNode Node){
        String OutPath = null;
        TreeNode[] path = Node.getPath();
        for (TreeNode pathnode : path){
            if (OutPath == null){
                OutPath = pathnode.toString();
            }else{
                OutPath = OutPath + " / " + pathnode.toString();
            }
        }
        return OutPath;
    }
    
    public static void setParent( DefaultMutableTreeNode Node, DefaultMutableTreeNode newParent ){
        if ( null == Node ) return;                         // No node selected   
        if ( null == newParent ) return;                    // No Parent provided    
        DefaultMutableTreeNode oldParent = (DefaultMutableTreeNode)Node.getParent();   
        oldParent.remove(Node);   
        newParent.add(Node);  
        System.out.println("ADM: setParent: node = '" + Node + "' oldParent = '" + oldParent +"' newParent = '" + newParent + "'");
    }
    
    public static void moveNode( DefaultMutableTreeNode Node, int aDelta ){
        if ( null == Node ) return;                         // No node selected   
        DefaultMutableTreeNode lParent = (DefaultMutableTreeNode)Node.getParent();   
        if ( null == lParent ) return;                      // Cannot move the Root!   
        int lOldIndex = lParent.getIndex( Node );   
        int lNewIndex = lOldIndex + aDelta;   
        if ( lNewIndex < 0 ) return;                        // Cannot move first child up   
        if ( lNewIndex >= lParent.getChildCount() ) return; // Cannot move last child down   
        lParent.remove(Node);   
        lParent.insert( Node, lNewIndex );  
        //TODO: need to save the indexes of all this parents children
    }  
    
}
