/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

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
        Parent = util.TopMenu;
        Name = bName;
        ButtonText = util.ButtonTextDefault;
        SubMenu = null;
        ActionType = util.ActionTypeDefault;
        ActionAttribute = null;
        BGImageFile = null;
        BGImageFilePath = null;
        IsDefault = false;
        IsActive = true;
        HasSubMenu = false;
        SortKey = 0;
        MenuNodeList.put(Name, this);
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
        createNodes();
        System.out.println("ADM: TEST NODES: ");
        ListChildren(root);
        
        //find by Node
        FindNode(root, Testing) ;
        DefaultMutableTreeNode Child3 = FindNode(root, "Child1 - Child3") ;

        //move Child 3 up one
        moveNode(Child3,-1);
        ListChildren(root);
        moveNode(Child3,-1);
        ListChildren(root);
        moveNode(Child3,-1);
        ListChildren(root);

        setParent(Child3, FindNode(root, "Child2"));
        ListChildren(root);
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
                System.out.println("ADM: FindNode: '" + Node + "' found = '" + child + "' childcount = '" + child.getChildCount() + "' Parent = '" + child.getParent() + "' Level = '" + child.getLevel() + "' Leaf = '" + child.isLeaf() + "'"  );
                return child;                          
            } 
        }         
        System.out.println("ADM: FindNode: '" + Node + "' not found.");
        return null;
    }
    
    public static DefaultMutableTreeNode FindNode(DefaultMutableTreeNode Root, String NodeKey){
        Enumeration<DefaultMutableTreeNode> en = Root.preorderEnumeration();
        while (en.hasMoreElements())   {
            DefaultMutableTreeNode child = en.nextElement();
            if(NodeKey.equals(child.getUserObject().toString())) 
            { 
                //tree node with string found 
                System.out.println("ADM: FindNode: '" + NodeKey + "' found = '" + child + "' childcount = '" + child.getChildCount() + "' Parent = '" + child.getParent() + "' Level = '" + child.getLevel() + "' Leaf = '" + child.isLeaf() + "'"  );
                return child;                          
            } 
        }         
        System.out.println("ADM: FindNode: '" + NodeKey + "' not found.");
        return null;
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
