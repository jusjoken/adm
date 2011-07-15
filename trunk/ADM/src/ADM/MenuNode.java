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
    private DefaultMutableTreeNode NodeItem;
    public static Integer SortKeyCounter = 0;
    public static Map<String,MenuNode> MenuNodeList = new LinkedHashMap<String,MenuNode>();
    public static DefaultMutableTreeNode root = new DefaultMutableTreeNode(util.OptionNotFound);

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
        SetBGImageFileandPath(bBGImageFile);
        IsDefault = bIsDefault;
        IsActive = bIsActive;
        SortKey = bSortKey;
        MenuNodeList.put(Name, this);
    }
    
    @Override
    public String toString(){
        return Name;
    }

    public static String GetMenuItemAction(String Name){
        //System.out.println("ADM: GetMenuItemAction for '" + Name + "' = '" + MenuNodeList.get(Name).ActionAttribute + "'");
        return MenuNodeList.get(Name).ActionAttribute;
    }

    public static void SetMenuItemAction(String Name, String Setting){
        if (GetMenuItemActionType(Name).equals(Action.BrowseVideoFolder)){
            //ensure the Folder string ends in a "/" unless it's blank
            if (Setting.isEmpty() || !Setting.endsWith("/")){
                Setting = Setting + "/";
            }
            if (Setting.equals("/")){
                Setting = null;
            }
        }
        Save(Name, "Action", Setting);
    }

    public static String GetMenuItemActionType(String Name){
        return MenuNodeList.get(Name).ActionType;
    }

    public static void SetMenuItemActionType(String Name, String Setting){
        Save(Name, "ActionType", Setting);
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
    
    public static String GetMenuItemBGImageFileButtonText(String Name){
        return util.GetSageBGVariablesButtonText(MenuNodeList.get(Name).BGImageFile);
    }
    
    public static String GetMenuItemBGImageFile(String Name){
        return MenuNodeList.get(Name).BGImageFile;
    }

    public static String GetMenuItemBGImageFilePath(String Name){
        return MenuNodeList.get(Name).BGImageFilePath;
    }

    public static void SetMenuItemBGImageFile(String Name, String Setting){
        if (Setting.equals(util.ListNone) || Setting==null){
            Save(Name, "BGImageFile", null);
        }else{
            Save(Name, "BGImageFile", Setting);
        }
    }

    public static String GetMenuItemButtonText(String Name){
        if (Name.equals(util.TopMenu)){
            return "Top Level";
        }else{
            return MenuNodeList.get(Name).ButtonText;
        }
    }

    public static String GetMenuItemButtonTextNewTest(String Name){
        if (MenuNodeList.get(Name).ButtonText.equals(util.ButtonTextDefault)){
            return "";
        }else{
            return MenuNodeList.get(Name).GetMenuItemButtonText(Name);
        }
    }

    public static void SetMenuItemButtonText(String Name, String Setting){
        Save(Name, "ButtonText", Setting);
    }

    public static Boolean GetMenuItemHasChildren(String Name){
        return !MenuNodeList.get(Name).NodeItem.isLeaf();
    }

    public static Boolean GetMenuItemHasSubMenu(String Name){
        return MenuNodeList.get(Name).HasSubMenu;
    }

    public static void SetMenuItemHasSubMenu(String Name, Boolean Setting){
        Save(Name, "HasSubMenu", Setting.toString());
    }

    public static Boolean GetMenuItemIsActive(String Name){
        return MenuNodeList.get(Name).IsActive;
    }

    public static String GetMenuItemIsActiveIncludingParentFormatted(String Name){
        if (!MenuNodeList.get(Name).IsActive){
            return "No";
        }else if (GetMenuItemIsActiveIncludingParent(Name)){
            return "Yes";
        }
        // in this case this item is active BUT the parent is not
        return "Yes (but Parent is not Active)";
    }
    
    public static Boolean GetMenuItemIsActiveIncludingParent(String Name){
        //if this item is inactive then return FALSE
        if (!MenuNodeList.get(Name).IsActive){
            return Boolean.FALSE;
        }else{
            //if the level is 1 then just return the item setting
            if (MenuNodeList.get(Name).NodeItem.getLevel()==1){
                return MenuNodeList.get(Name).IsActive;
            }else if (MenuNodeList.get(Name).NodeItem.getLevel()==2){
                //for level 2 just return the parents setting
                return MenuNodeList.get(MenuNodeList.get(Name).Parent).IsActive;
            }else{
                //for level 3 check the level 2 parent
                if (!MenuNodeList.get(MenuNodeList.get(Name).Parent).IsActive){
                    return Boolean.FALSE;
                }else{
                    //now just return the level 1 parent setting
                    return MenuNodeList.get(MenuNodeList.get(MenuNodeList.get(Name).Parent).Parent).IsActive;
                }
            }
        }
    }

    public static void SetMenuItemIsActive(String Name, Boolean Setting){
        Save(Name, "IsActive", Setting.toString());
    }

    public static Boolean GetMenuItemIsDefault(String Name){
        return MenuNodeList.get(Name).IsDefault;
    }

    public static void SetMenuItemIsDefault(String Name, Boolean Setting){
        //System.out.println("ADM: SetMenuItemIsDefault: Name '" + Name + "' Setting '" + Setting + "'");
        if (Setting==Boolean.TRUE){
            //System.out.println("ADM: SetMenuItemIsDefault: true Name '" + Name + "' Setting '" + Setting + "'");
            //first clear existing Default settings for Menu Items with the same parent 
            ClearSubMenuDefaults(MenuNodeList.get(Name).Parent);
            Save(Name, "IsDefault", Setting.toString());
        }else{
            //System.out.println("ADM: SetMenuItemIsDefault: false Name '" + Name + "' Setting '" + Setting + "'");
            Save(Name, "IsDefault", Setting.toString());
            //ensure at least 1 item remaining is a default
            ValidateSubMenuDefault(MenuNodeList.get(Name).Parent);
        }
    }

    public static void ValidateSubMenuDefault(String bParent){
        //ensure that 1 and only 1 item is set as the default
        Boolean FoundDefault = Boolean.FALSE;
        
        if (MenuNodeList.get(bParent).NodeItem.getChildCount()>0){

            Enumeration<DefaultMutableTreeNode> en = MenuNodeList.get(bParent).NodeItem.preorderEnumeration();
            while (en.hasMoreElements())   {
                DefaultMutableTreeNode child = en.nextElement();
                MenuNode tMenu = (MenuNode)child.getUserObject();
                if (tMenu.IsDefault){
                    if (FoundDefault){
                    //Save setting
                        Save(tMenu.Name, "IsDefault", Boolean.FALSE.toString());
                    }else{
                        FoundDefault = Boolean.TRUE;
                    }
                }
            }         
            if (!FoundDefault){
                //no default found so set the first item as the default
                DefaultMutableTreeNode firstChild = (DefaultMutableTreeNode)MenuNodeList.get(bParent).NodeItem.getFirstChild();
                MenuNode tMenu = (MenuNode)firstChild.getUserObject();
                System.out.println("ADM: ValidateSubMenuDefault for '" + bParent + "' : no Default found so setting first = '" + tMenu.Name + "'");
                Save(tMenu.Name, "IsDefault", Boolean.TRUE.toString());
            }else {
                System.out.println("ADM: ValidateSubMenuDefault for '" + bParent + "' : Default already set");
            }
        }else{
            //no subMenu items so make sure this parent's SubMenu settings are correct
            SetMenuItemSubMenu(bParent, util.ListNone);
            SetMenuItemHasSubMenu(bParent, Boolean.FALSE);
            System.out.println("ADM: ValidateSubMenuDefault for '" + bParent + "' : no SubMenu items found");
        }
    }
        
    //clear all defaults for a submenu - used prior to setting a new default to ensure there is not more than one
    public static void ClearSubMenuDefaults(String bParent){
        if (MenuNodeList.get(bParent).NodeItem.getChildCount()>0){
            Enumeration<DefaultMutableTreeNode> en = MenuNodeList.get(bParent).NodeItem.preorderEnumeration();
            while (en.hasMoreElements())   {
                DefaultMutableTreeNode child = en.nextElement();
                MenuNode tMenu = (MenuNode)child.getUserObject();
                if (tMenu.IsDefault){
                    //Save setting
                    Save(tMenu.Name, "IsDefault", Boolean.FALSE.toString());
                }
            }         
        }
        System.out.println("ADM: ClearSubMenuDefaults for '" + bParent + "' '" + MenuNodeList.get(bParent).NodeItem.getChildCount() + "' cleared");
    }
    
    public static String GetSubMenuDefault(String bParent){
        if (MenuNodeList.get(bParent).NodeItem.getChildCount()>0){
            Enumeration<DefaultMutableTreeNode> en = MenuNodeList.get(bParent).NodeItem.preorderEnumeration();
            while (en.hasMoreElements())   {
                DefaultMutableTreeNode child = en.nextElement();
                MenuNode tMenu = (MenuNode)child.getUserObject();
                if (tMenu.IsDefault){
                    System.out.println("ADM: GetSubMenuDefault for '" + bParent + "' Default = '" + tMenu.Name + "'");
                    return tMenu.Name;
                }
            }         
        }
        System.out.println("ADM: GetSubMenuDefault for '" + bParent + "' - none found");
        return "";
    }

    public static Integer GetMenuItemLevel(String Name){
        return MenuNodeList.get(Name).NodeItem.getLevel();
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
    

    public static void LoadMenuItemsFromSage(){
        String PropLocation = "";
        
        //find all MenuItem Name entries from the SageTV properties file
        String[] MenuItemNames = sagex.api.Configuration.GetSubpropertiesThatAreBranches(util.SagePropertyLocation);
        
        if (MenuItemNames.length>0){
            //clear the existing MenuItems from the list
            MenuNodeList.clear();
            root.removeAllChildren();

            //create and store the top menu node
            MenuNode rootNode = new MenuNode(util.TopMenu);
            root = new DefaultMutableTreeNode(rootNode);
            rootNode.NodeItem = root;
            
            //load MenuItems
            for (String tMenuItemName : MenuItemNames){
                PropLocation = util.SagePropertyLocation + tMenuItemName;
                MenuNode NewMenuItem = new MenuNode(tMenuItemName);
                NewMenuItem.ActionAttribute = sagex.api.Configuration.GetProperty(PropLocation + "/Action", null);
                NewMenuItem.ActionType = sagex.api.Configuration.GetProperty(PropLocation + "/ActionType", util.ActionTypeDefault);
                NewMenuItem.SetBGImageFileandPath(sagex.api.Configuration.GetProperty(PropLocation + "/BGImageFile", null));
                NewMenuItem.ButtonText = sagex.api.Configuration.GetProperty(PropLocation + "/ButtonText", util.ButtonTextDefault);
                NewMenuItem.Name = sagex.api.Configuration.GetProperty(PropLocation + "/Name", tMenuItemName);
                NewMenuItem.Parent = sagex.api.Configuration.GetProperty(PropLocation + "/Parent", "xTopMenu");
                NewMenuItem.setSortKey(sagex.api.Configuration.GetProperty(PropLocation + "/SortKey", null));
                NewMenuItem.SubMenu = sagex.api.Configuration.GetProperty(PropLocation + "/SubMenu", null);
                NewMenuItem.HasSubMenu = Boolean.getBoolean(sagex.api.Configuration.GetProperty(PropLocation + "/HasSubMenu", "false"));
                NewMenuItem.IsDefault = Boolean.getBoolean(sagex.api.Configuration.GetProperty(PropLocation + "/IsDefault", "false"));
                NewMenuItem.IsActive = Boolean.getBoolean(sagex.api.Configuration.GetProperty(PropLocation + "/IsActive", "true"));
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
                //root.add(new DefaultMutableTreeNode(aNode));
                InsertNode(root, aNode);
                System.out.println("ADM: AddNode: node '" + aNode.ButtonText + "' not found so adding to ROOT");
            }else{
                AddNode(MenuNodeList.get(aNode.Parent));
                DefaultMutableTreeNode tParent = FindNode(root, aNode.Parent);
                //tParent.add(new DefaultMutableTreeNode(aNode));
                InsertNode(tParent, aNode);
                System.out.println("ADM: AddNode: node '" + aNode.ButtonText + "' not found so adding");
            }
        }else{
            System.out.println("ADM: AddNode: node '" + aNode.ButtonText + "' already exists");
        }
    }
    
    private static void InsertNode(DefaultMutableTreeNode iParent, MenuNode iNode){
        //insert the node according to the SortKey value
        if ( iParent.getChildCount() != 0 ) {

            DefaultMutableTreeNode tlastChild = (DefaultMutableTreeNode)iParent.getFirstChild() ;
            MenuNode lastChild = (MenuNode)tlastChild.getUserObject() ;
            //MenuNode newChildA = (MenuNode) iNode ;
            if ( iNode.SortKey < lastChild.SortKey ) {
                // Its at the top of the list
                InsertNode(iParent,iNode,0);
            }
            else if ( iParent.getChildCount() == 1 ) {
                // There is only one element and since it ain't less than then well put it after
                InsertNode(iParent,iNode,1);
            } else { 
                // we gotta go look for the right spot to insert it
                Boolean done = Boolean.FALSE ;
                for ( int i = 1 ; i < iParent.getChildCount() && !done ; i++ ) {

                    DefaultMutableTreeNode tnextChild = (DefaultMutableTreeNode)iParent.getChildAt(i) ;
                    MenuNode nextChild = (MenuNode)tnextChild.getUserObject() ;
                    if (( iNode.SortKey > lastChild.SortKey ) && ( iNode.SortKey < nextChild.SortKey ) ){
                        // Ok it needs to go between these two
                        InsertNode(iParent,iNode,i);
                        done = Boolean.TRUE;
                    }
                }
                if ( !done ) { // didn't find a place to insert the node must be the last one
                    InsertNode(iParent,iNode,iParent.getChildCount());
                }
            }            
        }else{
            //no children so just do an add
            InsertNode(iParent,iNode,-1);
        }
    }
    
    private static void InsertNode(DefaultMutableTreeNode iParent, MenuNode iNode, Integer iLocation){
        DefaultMutableTreeNode tNode = new DefaultMutableTreeNode(iNode);
        iNode.NodeItem = tNode;
        if (iLocation>=0){
            //do an insert
            iParent.insert(tNode, iLocation);
        }else{
            //do an add for -1
            iParent.add(tNode);
        }
    }
    
    public static void Test(){
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

    @SuppressWarnings("unchecked")
    public static void ListChildren(DefaultMutableTreeNode Node){
        Enumeration<DefaultMutableTreeNode> en = Node.preorderEnumeration();
        String tButtonText;
        while (en.hasMoreElements())   {
            DefaultMutableTreeNode child = en.nextElement();
            MenuNode tMenu = (MenuNode)child.getUserObject();
            tButtonText = tMenu.ButtonText;
            System.out.println("ADM: TEST NODES: Child = '" + child + "' SortKey = '" + tMenu.SortKey + "' childcount = '" + child.getChildCount() + "' Parent = '" + child.getParent() + "' Level = '" + child.getLevel() + "' Leaf = '" + child.isLeaf() + "' HasChildren = '" + GetMenuItemHasChildren(tMenu.Name) + "' buttonText = '" + tButtonText + "' Path = '" + GetPath(child) + "'"  );
        }         
    }
    
    @SuppressWarnings("unchecked")
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
    
    @SuppressWarnings("unchecked")
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
    
    @SuppressWarnings("unchecked")
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
    
    private static void Save(String Name, String PropType, String Setting){
        String PropLocation = util.SagePropertyLocation + Name;
        sagex.api.Configuration.SetProperty(PropLocation + "/" + PropType, Setting);
        //no save the specifc node field change
        if (PropType.equals("Action")){
            MenuNodeList.get(Name).ActionAttribute = Setting;
        }else if (PropType.equals("ActionType")){
            MenuNodeList.get(Name).ActionType = Setting;
        }else if (PropType.equals("BGImageFile")){
            MenuNodeList.get(Name).BGImageFile = Setting;
        }else if (PropType.equals("ButtonText")){
            MenuNodeList.get(Name).ButtonText = Setting;
        }else if (PropType.equals("HasSubMenu")){
            MenuNodeList.get(Name).HasSubMenu = Boolean.getBoolean(Setting);
        }else if (PropType.equals("IsActive")){
            MenuNodeList.get(Name).IsActive = Boolean.getBoolean(Setting);
        }else if (PropType.equals("IsDefault")){
            MenuNodeList.get(Name).IsDefault = Boolean.getBoolean(Setting);
//        }else if (PropType.equals("")){
//            MenuNodeList.get(Name). = Setting;
//        }else if (PropType.equals("")){
//            MenuNodeList.get(Name). = Setting;
//        }else if (PropType.equals("")){
//            MenuNodeList.get(Name). = Setting;
//        }else if (PropType.equals("")){
//            MenuNodeList.get(Name). = Setting;
            
        }
        
        
        System.out.println("ADM: Save completed for '" + PropType + "' '" + Name + "' = '" + Setting + "'");
    }
    
}
