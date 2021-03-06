package ADM;


import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import sagex.UIContext;

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
    public static void SaveFileFolderDetails(String CurFolderStyle, String BrowserFileCell){
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", "FileFolder");

        util.SetProperty(SageCurrentMenuItemPropertyLocation + "CurFolderStyle", CurFolderStyle);
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "BrowserFileCell", BrowserFileCell);
        System.out.println("ADM: cSaveFileFolderDetails: CurFolderStyle '" + CurFolderStyle + "' BrowserFileCell '" + BrowserFileCell + "'");
    }
    
    public static String GetFileFolderDetails(){
        //determine if Combined mode is on as the path is created differently
        String BrowserFileCell = util.GetProperty(SageCurrentMenuItemPropertyLocation + "BrowserFileCell", util.OptionNotFound);
        return BrowserFileCell;
    }
    
    public static Boolean IsFileFolderStyleValid(){
        //determine if Combined mode is on as the path is created differently
        String CurFolderStyle = util.GetProperty(SageCurrentMenuItemPropertyLocation + "CurFolderStyle", util.OptionNotFound);
        if (Action.GetFileBrowserType(CurFolderStyle).equals(util.OptionNotFound)){
            System.out.println("ADM: cIsFileFolderStyleValid: invalid Style = '" + CurFolderStyle + "'");
            return Boolean.FALSE;
        }else{
            System.out.println("ADM: cIsFileFolderStyleValid: valid Style = '" + CurFolderStyle + "'");
            return Boolean.TRUE;
        }
    }
    
    public static String GetFileFolderDetailsButtonText(){
        String ButtonText = GetFileFolderDetails();
        ButtonText = ButtonText.replace("/", " ").trim();
        ButtonText = ButtonText.replace("\\", " ").trim();
        if (ButtonText.isEmpty()){
            ButtonText = "Root";
        }
        return ButtonText;
    }
    
    public static Collection<String> GetFileFolderDetailsParentList(){
        return MenuNode.GetMenuItemParentList();
    }
    
    //create a new Menu Item from the current Video Folder Menu Item details
    public static String CreateMenuItemfromFileFolderCopyDetails(String Parent){
        //Create a new MenuItem with defaults
        String CurFolderStyle = util.GetProperty(SageCurrentMenuItemPropertyLocation + "CurFolderStyle", util.OptionNotFound);
        if (!Action.GetFileBrowserType(CurFolderStyle).equals(util.OptionNotFound)){
            String tMenuItemName = MenuNode.NewMenuItem(Parent, 0);

            //set all the copy details
            MenuNode.SetMenuItemAction(tMenuItemName,GetFileFolderDetails());
            MenuNode.SetMenuItemActionType(tMenuItemName,Action.GetFileBrowserType(CurFolderStyle));

            MenuNode.SetMenuItemBGImageFile(tMenuItemName,util.ListNone);
            MenuNode.SetMenuItemButtonText(tMenuItemName,GetFileFolderDetailsButtonText());
            MenuNode.SetMenuItemName(tMenuItemName);
            MenuNode.SetMenuItemSubMenu(tMenuItemName,util.ListNone);
            MenuNode.SetMenuItemIsActive(tMenuItemName,util.TriState.YES);

            System.out.println("ADM: cCreateMenuItemfromFileFolderCopyDetails: created '" + tMenuItemName + "' for Parent = '" + Parent + "'");
            return tMenuItemName;
            
        }else{
            System.out.println("ADM: cCreateMenuItemfromFileFolderCopyDetails: invalid CurFolderStyle '" + CurFolderStyle + "'");
            return util.OptionNotFound;
        }
    }
    
    //save the current Folder item details to sage properties to assist the copy function
    public static void SaveVideoFolderDetails(String CurFolder, String VideoItem){
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", "Folder");

        if (CurFolder==null || CurFolder.equals("null")){
            util.SetProperty(SageCurrentMenuItemPropertyLocation + "CurFolder", null);
            util.SetProperty(SageCurrentMenuItemPropertyLocation + "VideoItem", VideoItem);
        }else{
            util.SetProperty(SageCurrentMenuItemPropertyLocation + "CurFolder", CurFolder);
            util.SetProperty(SageCurrentMenuItemPropertyLocation + "VideoItem", VideoItem);
        }
        System.out.println("ADM: cSaveVideoFolderDetails: CurFolder '" + CurFolder + "' VideoItem '" + VideoItem + "'");
    }
    
    public static String GetVideoFolderDetails(){
        //determine if Combined mode is on as the path is created differently
        String CurFolder = util.GetProperty(SageCurrentMenuItemPropertyLocation + "CurFolder", util.OptionNotFound);
        String VideoItem = util.GetProperty(SageCurrentMenuItemPropertyLocation + "VideoItem", util.OptionNotFound);
        
        if (util.GetProperty("video_lib/folder_style", "xCombined").equals("xCombined")){
            if(CurFolder==null || CurFolder.equals(util.OptionNotFound)){
                return VideoItem + "/";
            }else{
                return CurFolder + VideoItem + "/";
            }
        }else{
            if(CurFolder==null || CurFolder.equals(util.OptionNotFound)){
                return VideoItem;
            }else{
                return sagex.api.Utility.CreateFilePath( CurFolder, VideoItem ).toString();
            }
        }
    }
    
    public static String GetVideoFolderDetailsButtonText(){
        String ButtonText = GetVideoFolderDetails();
        ButtonText = ButtonText.replace("/", " ").trim();
        ButtonText = ButtonText.replace("\\", " ").trim();
        if (ButtonText.isEmpty()){
            ButtonText = "Root";
        }
        return ButtonText;
    }
    
    public static Collection<String> GetVideoFolderDetailsParentList(){
        return MenuNode.GetMenuItemParentList();
    }
    
    //create a new Menu Item from the current Video Folder Menu Item details
    public static String CreateMenuItemfromVideoFolderCopyDetails(String Parent){
        //Create a new MenuItem with defaults
        String tMenuItemName = MenuNode.NewMenuItem(Parent, 0);

        //set all the copy details
        MenuNode.SetMenuItemAction(tMenuItemName,GetVideoFolderDetails());
        MenuNode.SetMenuItemActionType(tMenuItemName,Action.BrowseVideoFolder);

        MenuNode.SetMenuItemBGImageFile(tMenuItemName,util.ListNone);
        MenuNode.SetMenuItemButtonText(tMenuItemName,GetVideoFolderDetailsButtonText());
        MenuNode.SetMenuItemName(tMenuItemName);
        MenuNode.SetMenuItemSubMenu(tMenuItemName,util.ListNone);
        MenuNode.SetMenuItemIsActive(tMenuItemName,util.TriState.YES);
        
        System.out.println("ADM: cCreateMenuItemfromVideoFolderCopyDetails: created '" + tMenuItemName + "' for Parent = '" + Parent + "'");
        return tMenuItemName;
        
    }
    
    //save the current item details to sage properties to assist the copy function
    public static void SaveMenuItemDetails(String ButtonText, String SubMenu, String CurrentWidgetSymbol, Integer Level){
        //clear previously stored Menu Item Details
        util.RemovePropertyAndChildren(SageCurrentMenuItemPropertyLocation);
        //save the current details
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", "MenuItem");
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "WidgetSymbol", CurrentWidgetSymbol);
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "ButtonText", ButtonText);
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "SubMenu", SubMenu);
        util.SetProperty(SageCurrentMenuItemPropertyLocation + "Level", Level.toString());
        
        //determine if there is an Action Widget for this Menu Item
        String ActionWidget = null;
        UIContext tUIContext = new UIContext(sagex.api.Global.GetUIContextName());
        Object[] Children = sagex.api.WidgetAPI.GetWidgetChildren(tUIContext, CurrentWidgetSymbol);
        for (Object Child : Children){
            //System.out.println("ADM: cSaveCurrentMenuItemDetails: WidgetName = '" + sagex.api.WidgetAPI.GetWidgetName(MyUIContext,Child) + "' WidgetType '" + sagex.api.WidgetAPI.GetWidgetType(MyUIContext,Child) + "'");
            List<String> validActions = new LinkedList<String>();
            validActions.add("Action");
            validActions.add("Menu");
            validActions.add("OptionsMenu");
            validActions.add("Conditional");
            if (validActions.contains(sagex.api.WidgetAPI.GetWidgetType(tUIContext,Child))){
                //found an action so save it and leave
                ActionWidget = sagex.api.WidgetAPI.GetWidgetSymbol(tUIContext,Child);
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
                    if ("Attribute".equals(sagex.api.WidgetAPI.GetWidgetType(new UIContext(sagex.api.Global.GetUIContextName()),Child))){
                        if ("ViewFilter".equals(sagex.api.WidgetAPI.GetWidgetName(new UIContext(sagex.api.Global.GetUIContextName()),Child))){
                            tViewFilter = sagex.api.WidgetAPI.GetWidgetProperty(new UIContext(sagex.api.Global.GetUIContextName()),Child,"Value");
                            //get rid of the imbedded " (quotes) in the returned string
                            tViewFilter = tViewFilter.replace("\"", "");
                        }else if ("ViewTitlePostfixText".equals(sagex.api.WidgetAPI.GetWidgetName(new UIContext(sagex.api.Global.GetUIContextName()),Child))){
                            tViewTitlePostfixText = sagex.api.WidgetAPI.GetWidgetProperty(new UIContext(sagex.api.Global.GetUIContextName()),Child,"Value");
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
                
            }else if (Action.CustomAction.WidgetSymbols.contains(ActionWidget)){
                //CustomAction found so determine which one 
                Boolean tFound = Boolean.FALSE;
                for (Object Child : Children){
                    //check only Attribute Widgets
                    if ("Attribute".equals(sagex.api.WidgetAPI.GetWidgetType(new UIContext(sagex.api.Global.GetUIContextName()),Child))){
                        //append the widget Value to the widget Name to lookup a unique CopyMode name
                        String tName = sagex.api.WidgetAPI.GetWidgetName(new UIContext(sagex.api.Global.GetUIContextName()),Child);
                        String tVar = sagex.api.WidgetAPI.GetWidgetProperty(new UIContext(sagex.api.Global.GetUIContextName()),Child,"Value");
                        tVar = tVar.replace("\"", "");
                        String tCheckString = Action.CustomAction.UniqueID(tName,tVar);
                        if (Action.CustomAction.CopyModeUniqueIDs.contains(tCheckString)){
                            FinalAction = tVar;
                            tFound = Boolean.TRUE;
                            break;
                        }
                    }
                }
                if (tFound){
                    FinalType = Action.StandardMenuAction;
                }
            }else if (Action.GetActionList(Action.StandardMenuAction).contains(ActionWidget)){
                FinalType = Action.StandardMenuAction;
            }else if (Action.GetActionList(Action.DiamondDefaultFlows).contains(ActionWidget)){
                //save the Widget Symbol as the Action
                FinalType = Action.DiamondDefaultFlows;
            }else{
            }
            util.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", FinalType);
            util.SetProperty(SageCurrentMenuItemPropertyLocation + "Action", FinalAction);
        }else{
            //no action found so set to DoNothing
            util.SetProperty(SageCurrentMenuItemPropertyLocation + "Type", Action.ActionTypeDefault);
        }
        System.out.println("ADM: cSaveCurrentMenuItemDetails: ButtonText '" + ButtonText + "' SubMenu '" + SubMenu + "' WidgetSymbol '" + CurrentWidgetSymbol + "' Level '" + Level + "' Type ='" + FinalType + "' Action = '" + FinalAction + "'");
    }
    
    //create a new Menu Item from the current Menu Item details
    public static String CreateMenuItemfromCopyDetails(String Parent){
        //Create a new MenuItem with defaults
        String tMenuItemName = MenuNode.NewMenuItem(Parent, 0);

        //set all the copy details
        if (Action.IsValidAction(GetMenuItemDetailsType())){
            MenuNode.SetMenuItemAction(tMenuItemName,GetMenuItemDetailsAction());
            MenuNode.SetMenuItemActionType(tMenuItemName,GetMenuItemDetailsType());
        }
        MenuNode.SetMenuItemBGImageFile(tMenuItemName,util.ListNone);
        MenuNode.SetMenuItemButtonText(tMenuItemName,GetMenuItemDetailsButtonText());
        MenuNode.SetMenuItemName(tMenuItemName);
        if (GetMenuItemDetailsSubMenu().equals(util.OptionNotFound)){
            MenuNode.SetMenuItemSubMenu(tMenuItemName,util.ListNone);
        }else{
            MenuNode.SetMenuItemSubMenu(tMenuItemName,GetMenuItemDetailsSubMenu());
        }
        MenuNode.SetMenuItemIsActive(tMenuItemName,util.TriState.YES);

        System.out.println("ADM: cCreateMenuItemfromCopyDetails: created '" + tMenuItemName + "' for Parent = '" + Parent + "'");
        return tMenuItemName;
        
    }
    
    public static String CreateMenuItemfromCopyDetails(){
        //default the Parent to TopMenu
        return CreateMenuItemfromCopyDetails(util.TopMenu);
    }
    
    public static Collection<String> GetMenuItemDetailsParentList(){
        if (GetMenuItemDetailsSubMenu().equals(util.OptionNotFound)){
            return MenuNode.GetMenuItemParentList();
        }else{
            return MenuNode.GetMenuItemParentList(GetMenuItemDetailsLevel());
        }
    }
    
    public static String GetMenuItemDetailsButtonText(){
        return util.GetProperty(SageCurrentMenuItemPropertyLocation + "ButtonText", util.OptionNotFound);
    }
    
    public static String GetMenuItemDetailsAction(){
        return util.GetProperty(SageCurrentMenuItemPropertyLocation + "Action", util.OptionNotFound);
    }
    
    public static String GetMenuItemDetailsType(){
        return util.GetProperty(SageCurrentMenuItemPropertyLocation + "Type", util.OptionNotFound);
    }
    
    public static Integer GetMenuItemDetailsLevel(){
        Integer tLevel = 0;
        try {
            tLevel = Integer.valueOf(util.GetProperty(SageCurrentMenuItemPropertyLocation + "Level", "0"));
        } catch (NumberFormatException ex) {
            System.out.println("ADM: cGetCurrentMenuItemDetailsLevel: error loading level: " + util.class.getName() + ex);
            tLevel = 0;
        }
        //System.out.println("ADM: cGetCurrentMenuItemDetailsLevel: returning level = '" + tLevel + "'");
        return tLevel;
    }
    
    public static String GetMenuItemDetailsWidgetSymbol(){
        return util.GetProperty(SageCurrentMenuItemPropertyLocation + "WidgetSymbol", util.OptionNotFound);
    }
    
    public static String GetMenuItemDetailsSubMenu(){
        return util.GetProperty(SageCurrentMenuItemPropertyLocation + "SubMenu", util.OptionNotFound);
    }
    
    

}
    
