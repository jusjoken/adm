package ADM;


import java.util.Collection;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jusjoken
 */
public class CopyMode {

    public static final String SageCurrentMenuItemPropertyLocation = "ADM/currmenuitem/";

    //save the current Folder item details to sage properties to assist the copy function
    public static void SaveVideoFolderDetails(String CurFolder, String FolderName){
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", "Folder");
        String FolderPath = "";
        if (FolderName!=null){
            FolderPath = FolderName;
        }

        if (CurFolder!=null){
            FolderPath = CurFolder + FolderPath;
        }
        //ensure the Folder string ends in a "/"
        if (FolderPath.isEmpty() || !FolderPath.endsWith("/")){
            FolderPath = FolderPath + "/";
        }
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "FolderName", FolderPath);
        
        System.out.println("ADM: SaveCurrentVideoFolderDetails: FolderName '" + FolderPath + "'");
    }
    
    public static String GetVideoFolderDetails(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "FolderName", util.OptionNotFound);
        
    }
    
    public static String GetVideoFolderDetailsButtonText(){
        String ButtonText = sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "FolderName", util.ButtonTextDefault);
        ButtonText = ButtonText.replace("/", " ").trim();
        if (ButtonText.isEmpty()){
            ButtonText = "Root";
        }
        return ButtonText;
    }
    
    public static Collection<String> GetVideoFolderDetailsParentList(){
        return MenuItem.GetMenuItemParentList();
    }
    
    //create a new Menu Item from the current Video Folder Menu Item details
    public static String CreateMenuItemfromVideoFolderCopyDetails(String Parent){
        //
        String tMenuItemName = util.GetNewMenuItemName();

        //Create a new MenuItem with defaults
        MenuItem NewMenuItem = new MenuItem(tMenuItemName);
        MenuItem.SetMenuItemAction(tMenuItemName,GetVideoFolderDetails());
        MenuItem.SetMenuItemActionType(tMenuItemName,Action.BrowseVideoFolder);

        MenuItem.SetMenuItemBGImageFile(tMenuItemName,util.ListNone);
        MenuItem.SetMenuItemButtonText(tMenuItemName,GetVideoFolderDetailsButtonText());
        MenuItem.SetMenuItemName(tMenuItemName);
        MenuItem.SetMenuItemParent(tMenuItemName,Parent);
        MenuItem.SetMenuItemSortKey(tMenuItemName,MenuItem.SortKeyCounter++);
        MenuItem.SetMenuItemSubMenu(tMenuItemName,util.ListNone);
        MenuItem.SetMenuItemHasSubMenu(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsDefault(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsActive(tMenuItemName,Boolean.TRUE);
        
        //Level needs to be 1 more than the Parent
        MenuItem.SetMenuItemLevel(tMenuItemName,MenuItem.GetMenuItemLevel(Parent)+1);
        
        System.out.println("ADM: CreateMenuItemfromVideoFolderCopyDetails: created '" + tMenuItemName + "' for Parent = '" + Parent + "'");
        return tMenuItemName;
        
    }
    
    //save the current item details to sage properties to assist the copy function
    public static void SaveMenuItemDetails(String ButtonText, String SubMenu, String CurrentWidgetSymbol, Integer Level){
        //clear previously stored Menu Item Details
        sagex.api.Configuration.RemovePropertyAndChildren(SageCurrentMenuItemPropertyLocation);
        //save the current details
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", "MenuItem");
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "WidgetSymbol", CurrentWidgetSymbol);
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "ButtonText", ButtonText);
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "SubMenu", SubMenu);
        sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Level", Level.toString());
        
        //determine if there is an Action Widget for this Menu Item
        String ActionWidget = null;
        Object[] Children = sagex.api.WidgetAPI.GetWidgetChildren(util.GetMyUIContext(), CurrentWidgetSymbol);
        for (Object Child : Children){
            //System.out.println("ADM: SaveCurrentMenuItemDetails: WidgetName = '" + sagex.api.WidgetAPI.GetWidgetName(MyUIContext,Child) + "' WidgetType '" + sagex.api.WidgetAPI.GetWidgetType(MyUIContext,Child) + "'");
            if ("Action".equals(sagex.api.WidgetAPI.GetWidgetType(util.GetMyUIContext(),Child))){
                //found an action so save it and leave
                ActionWidget = sagex.api.WidgetAPI.GetWidgetSymbol(util.GetMyUIContext(),Child);
                break;
            }
        }
        String FinalAction = ActionWidget;
        String FinalType = Action.WidgetbySymbol;
        if (ActionWidget!=null){
            //test for special Action Widget Symbols
            if (ActionWidget.equals(Action.GetWidgetSymbol(Action.TVRecordingView))){
                //TV RecordingsView found so save the view Type
                FinalType = Action.TVRecordingView;
                String tViewFilter = util.OptionNotFound;
                String tViewTitlePostfixText = util.OptionNotFound;
                for (Object Child : Children){
                    if ("Attribute".equals(sagex.api.WidgetAPI.GetWidgetType(util.GetMyUIContext(),Child))){
                        if ("ViewFilter".equals(sagex.api.WidgetAPI.GetWidgetName(util.GetMyUIContext(),Child))){
                            tViewFilter = sagex.api.WidgetAPI.GetWidgetProperty(util.GetMyUIContext(),Child,"Value");
                            //get rid of the imbedded " (quotes) in the returned string
                            tViewFilter = tViewFilter.replace("\"", "");
                        }else if ("ViewTitlePostfixText".equals(sagex.api.WidgetAPI.GetWidgetName(util.GetMyUIContext(),Child))){
                            tViewTitlePostfixText = sagex.api.WidgetAPI.GetWidgetProperty(util.GetMyUIContext(),Child,"Value");
                            //get rid of the imbedded " (quotes) in the returned string
                            tViewFilter = "xView" + tViewTitlePostfixText.replace("\"", "");
                            break;
                        }
                    }
                }
                //determine if a standard TV Recording view was found or one of the extra views (5,6,7,or 8)
                if (Action.GetActionList(Action.TVRecordingView).contains(tViewFilter)){
                    FinalAction = tViewFilter;
                }else{
                    FinalAction = "xAll";
                }
            }else if (ActionWidget.equals(Action.GetWidgetSymbol(Action.DiamondCustomFlows))){
                //Diamond custom flow found so save the Attribute value
                FinalType = Action.DiamondCustomFlows;
                FinalAction = util.OptionNotFound;
                
                //TODO: need to find a way to determine the selected Flow
                
            }else if (Action.GetActionList(Action.StandardMenuAction).contains(ActionWidget)){
                FinalType = Action.StandardMenuAction;
            }else if (Action.GetActionList(Action.DiamondDefaultFlows).contains(ActionWidget)){
                //save the Widget Symbol as the Action
                FinalType = Action.DiamondDefaultFlows;
            }else{
            }
            sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", FinalType);
            sagex.api.Configuration.SetProperty(SageCurrentMenuItemPropertyLocation + "Action", FinalAction);
        }else{
            //sagex.api.Configuration.RemoveProperty(SageCurrentMenuItemPropertyLocation + "Action");
        }
        System.out.println("ADM: SaveCurrentMenuItemDetails: ButtonText '" + ButtonText + "' SubMenu '" + SubMenu + "' WidgetSymbol '" + CurrentWidgetSymbol + "' Level '" + Level + "' Type ='" + FinalType + "' Action = '" + FinalAction + "'");
    }
    
    //create a new Menu Item from the current Menu Item details
    public static String CreateMenuItemfromCopyDetails(String Parent){
        //
        String tMenuItemName = util.GetNewMenuItemName();

        //Create a new MenuItem with defaults
        MenuItem NewMenuItem = new MenuItem(tMenuItemName);
        if (Action.IsValidAction(GetMenuItemDetailsType())){
            MenuItem.SetMenuItemAction(tMenuItemName,GetMenuItemDetailsAction());
            MenuItem.SetMenuItemActionType(tMenuItemName,GetMenuItemDetailsType());
        }
        MenuItem.SetMenuItemBGImageFile(tMenuItemName,util.ListNone);
        MenuItem.SetMenuItemButtonText(tMenuItemName,GetMenuItemDetailsButtonText());
        MenuItem.SetMenuItemName(tMenuItemName);
        MenuItem.SetMenuItemParent(tMenuItemName,Parent);
        MenuItem.SetMenuItemSortKey(tMenuItemName,MenuItem.SortKeyCounter++);
        if (GetMenuItemDetailsSubMenu().equals(util.OptionNotFound)){
            MenuItem.SetMenuItemSubMenu(tMenuItemName,util.ListNone);
            MenuItem.SetMenuItemHasSubMenu(tMenuItemName,Boolean.FALSE);
        }else{
            MenuItem.SetMenuItemSubMenu(tMenuItemName,GetMenuItemDetailsSubMenu());
            MenuItem.SetMenuItemHasSubMenu(tMenuItemName,Boolean.TRUE);
        }
        MenuItem.SetMenuItemIsDefault(tMenuItemName,Boolean.FALSE);
        MenuItem.SetMenuItemIsActive(tMenuItemName,Boolean.TRUE);
        
        //Level needs to be 1 more than the Parent
        MenuItem.SetMenuItemLevel(tMenuItemName,MenuItem.GetMenuItemLevel(Parent)+1);
        
        System.out.println("ADM: CreateMenuItemfromCopyDetails: created '" + tMenuItemName + "' for Parent = '" + Parent + "'");
        return tMenuItemName;
        
    }
    
    public static String CreateMenuItemfromCopyDetails(){
        //default the Parent to TopMenu
        return CreateMenuItemfromCopyDetails(util.TopMenu);
    }
    
    public static Collection<String> GetMenuItemDetailsParentList(){
        if (GetMenuItemDetailsSubMenu().equals(util.OptionNotFound)){
            return MenuItem.GetMenuItemParentList();
        }else{
            return MenuItem.GetMenuItemParentList(GetMenuItemDetailsLevel());
        }
    }
    
    public static String GetMenuItemDetailsButtonText(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "ButtonText", util.OptionNotFound);
    }
    
    public static String GetMenuItemDetailsAction(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "Action", util.OptionNotFound);
    }
    
    public static String GetMenuItemDetailsType(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "Type", util.OptionNotFound);
    }
    
    public static Integer GetMenuItemDetailsLevel(){
        Integer tLevel = 0;
        try {
            tLevel = Integer.valueOf(sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "Level", "0"));
        } catch (NumberFormatException ex) {
            System.out.println("ADM: GetCurrentMenuItemDetailsLevel: error loading level: " + util.class.getName() + ex);
            tLevel = 0;
        }
        //System.out.println("ADM: GetCurrentMenuItemDetailsLevel: returning level = '" + tLevel + "'");
        return tLevel;
    }
    
    public static String GetMenuItemDetailsWidgetSymbol(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "WidgetSymbol", util.OptionNotFound);
    }
    
    public static String GetMenuItemDetailsSubMenu(){
        return sagex.api.Configuration.GetProperty(SageCurrentMenuItemPropertyLocation + "SubMenu", util.OptionNotFound);
    }
    
    

}
    
