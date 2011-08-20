/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import sagex.UIContext;

/**
 *
 * @author jusjoken
 */
public class Action {

    private static final String Blank = "admActionBlank";
    private static final String VarNull = "VarNull";
    private static final String SageADMCustomActionsPropertyLocation = "ADM/custom_actions";
    private static final String UseAttributeValue = "UseAttributeValue";
    private static final String StandardActionListFile = "ADMStandardActions.properties";
    private static final String CustomActionListFile = "ADMCustomActions.properties";
    public static Map<String,CustomAction> SageMenuActions = new LinkedHashMap<String,CustomAction>();
    public static Map<String,String>  SageTVRecordingViews = new LinkedHashMap<String,String>();
    public static Map<String,String> DynamicLists = new LinkedHashMap<String,String>();
    public static final String SageTVRecordingViewsTitlePropertyLocation = "sagetv_recordings/view_title/";
    private String Type = "";
    private String ButtonText = "";
    private String Attribute = Blank;
    private String WidgetSymbol = Blank;
    private String FieldTitle = "";
    private static final ActionVariable BlankActionVariable = new ActionVariable(Blank, Blank, Blank);
    private ActionVariable EvalExpression = BlankActionVariable;
    private List<ActionVariable> ActionVariables = new LinkedList<ActionVariable>();
    private static final String VarTypeGlobal = "VarTypeGlobal";
    private static final String VarTypeStatic = "VarTypeStatic";
    private static final String VarTypeSetProp = "VarTypeSetProp";
    private Boolean AdvancedOnly = Boolean.FALSE;
    private Boolean DiamondOnly = Boolean.FALSE;
    private static Map<String,Action> ActionList = new LinkedHashMap<String,Action>();
    public static final String WidgetbySymbol = "ExecuteWidget";
    public static final String BrowseVideoFolder = "ExecuteBrowseVideoFolder";
    public static final String StandardMenuAction = "ExecuteStandardMenuAction";
    public static final String TVRecordingView = "ExecuteTVRecordingView";
    public static final String DiamondDefaultFlows = "ExecuteDiamondDefaultFlow";
    public static final String DiamondCustomFlows = "ExecuteDiamondCustomFlow";
    public static final String BrowseFileFolderLocal = "ExecuteBrowseFileFolderLocal";
    public static final String BrowseFileFolderServer = "ExecuteBrowseFileFolderServer";
    public static final String BrowseFileFolderImports = "ExecuteBrowseFileFolderImports";
    public static final String BrowseFileFolderRecDir = "ExecuteBrowseFileFolderRecDir";
    public static final String BrowseFileFolderNetwork = "ExecuteBrowseFileFolderNetwork";
    public static final String LaunchExternalApplication = "LaunchExternalApplication";
    public static final String DynamicList = "AddDynamicList";
    public static final String ActionTypeDefault = "DoNothing";
    public static final String DynamicTVRecordingsList = "admDynamicTVRecordingsList";
    public static final String DynamicVideoPlaylist = "admDynamicVideoPlaylist";
    

    public Action(String Type, Boolean DiamondOnly, Boolean AdvancedOnly, String ButtonText){
        this(Type,DiamondOnly,AdvancedOnly,ButtonText,"Action",Blank);
    }

    public Action(String Type, Boolean DiamondOnly, Boolean AdvancedOnly, String ButtonText, String FieldTitle){
        this(Type,DiamondOnly,AdvancedOnly,ButtonText,FieldTitle,Blank);
    }

    public Action(String Type, Boolean DiamondOnly, Boolean AdvancedOnly, String ButtonText, String FieldTitle, String WidgetSymbol){
        this.Type = Type;
        this.AdvancedOnly = AdvancedOnly;
        this.DiamondOnly = DiamondOnly;
        this.ButtonText = ButtonText;
        this.FieldTitle = FieldTitle;
        this.WidgetSymbol = WidgetSymbol;
    }
    
    public static void Init(){
        //Clear existing Actions if any
        ActionList.clear();
        //Create the Actions for ADM to use
        ActionList.put(ActionTypeDefault, new Action(ActionTypeDefault,Boolean.FALSE,Boolean.FALSE,"None"));

        ActionList.put(WidgetbySymbol, new Action(WidgetbySymbol,Boolean.FALSE,Boolean.TRUE,"Execute Widget by Symbol", "Action"));

        ActionList.put(BrowseVideoFolder, new Action(BrowseVideoFolder,Boolean.FALSE,Boolean.FALSE,"Video Browser with specific Folder","Video Browser Folder","OPUS4A-174637"));
        ActionList.get(BrowseVideoFolder).ActionVariables.add(new ActionVariable(VarTypeGlobal,"gCurrentVideoBrowserFolder", UseAttributeValue));


        ActionList.put(StandardMenuAction, new Action(StandardMenuAction,Boolean.FALSE,Boolean.FALSE,"Execute Standard Sage Menu Action", "Standard Action"));

        ActionList.put(TVRecordingView, new Action(TVRecordingView,Boolean.FALSE,Boolean.FALSE,"Launch Specific TV Recordings View", "TV Recordings View","OPUS4A-174116"));
        ActionList.get(TVRecordingView).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ViewFilter", UseAttributeValue));

        ActionList.put(DynamicList, new Action(DynamicList,Boolean.FALSE,Boolean.FALSE,"Dynamic List Item", "Dynamic List Type"));

        ActionList.put(DiamondDefaultFlows, new Action(DiamondDefaultFlows,Boolean.TRUE,Boolean.FALSE,"Diamond Default Flow", "Diamond Default Flow"));

        ActionList.put(DiamondCustomFlows, new Action(DiamondCustomFlows,Boolean.TRUE,Boolean.FALSE,"Diamond Custom Flow", "Diamond Custom Flow","AOSCS-679216"));
        ActionList.get(DiamondCustomFlows).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ViewCell", UseAttributeValue));

        ActionList.put(BrowseFileFolderLocal, new Action(BrowseFileFolderLocal,Boolean.FALSE,Boolean.FALSE,"File Browser: Local","Local File Path","BASE-51703"));
        ActionList.get(BrowseFileFolderLocal).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ForceReload", "true"));
        ActionList.get(BrowseFileFolderLocal).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_style", "xLocal"));
        ActionList.get(BrowseFileFolderLocal).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_folder/local", UseAttributeValue));

        ActionList.put(BrowseFileFolderServer, new Action(BrowseFileFolderServer,Boolean.FALSE,Boolean.FALSE,"File Browser: Server","Server File Path","BASE-51703"));
        ActionList.get(BrowseFileFolderServer).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ForceReload", "true"));
        ActionList.get(BrowseFileFolderServer).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_style", "xServer"));
        ActionList.get(BrowseFileFolderServer).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_folder/server", UseAttributeValue));

        ActionList.put(BrowseFileFolderImports, new Action(BrowseFileFolderImports,Boolean.FALSE,Boolean.FALSE,"File Browser: Imports","Imports File Path","BASE-51703"));
        ActionList.get(BrowseFileFolderImports).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ForceReload", "true"));
        ActionList.get(BrowseFileFolderImports).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_style", "xImports"));
        ActionList.get(BrowseFileFolderImports).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_folder/imports", UseAttributeValue));

        ActionList.put(BrowseFileFolderRecDir, new Action(BrowseFileFolderRecDir,Boolean.FALSE,Boolean.FALSE,"File Browser: Recordings","Recording File Path","BASE-51703"));
        ActionList.get(BrowseFileFolderRecDir).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ForceReload", "true"));
        ActionList.get(BrowseFileFolderRecDir).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_style", "xRecDirs"));
        ActionList.get(BrowseFileFolderRecDir).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_folder/rec_dirs", UseAttributeValue));

        ActionList.put(BrowseFileFolderNetwork, new Action(BrowseFileFolderNetwork,Boolean.FALSE,Boolean.FALSE,"File Browser: Network","Network File Path","BASE-51703"));
        ActionList.get(BrowseFileFolderNetwork).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ForceReload", "true"));
        ActionList.get(BrowseFileFolderNetwork).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_style", "xNetwork"));
        ActionList.get(BrowseFileFolderNetwork).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_folder/network", UseAttributeValue));

        ActionList.put(LaunchExternalApplication, new Action(LaunchExternalApplication,Boolean.FALSE,Boolean.FALSE,"Launch External Application", "Application Settings"));

        //also load the actions lists - only needs loaded at startup
        //clear the lists
        SageMenuActions.clear();
        CustomAction.ActionListSorted.clear();
        CustomAction.CopyModeUniqueIDs.clear();
        CustomAction.WidgetSymbols.clear();

        LoadStandardActionList();
        LoadSageCustomMenuActions();
        LoadDynamicLists();

        Diamond.LoadDiamondDefaultFlows();

        LoadSageTVRecordingViews();
    }
    
    public static String GetWidgetbySymbol(){ return WidgetbySymbol; }
    public static String GetBrowseVideoFolder(){ return BrowseVideoFolder; }
    public static String GetStandardMenuAction(){ return StandardMenuAction; }
    public static String GetTVRecordingView(){ return TVRecordingView; }
    public static String GetDiamondCustomFlows(){ return DiamondCustomFlows; }
    public static String GetDiamondDefaultFlows(){ return DiamondDefaultFlows; }
    public static String GetBrowseFileFolderLocal(){ return BrowseFileFolderLocal; }
    public static String GetBrowseFileFolderServer(){ return BrowseFileFolderServer; }
    public static String GetBrowseFileFolderImports(){ return BrowseFileFolderImports; }
    public static String GetBrowseFileFolderRecDir(){ return BrowseFileFolderRecDir; }
    public static String GetBrowseFileFolderNetwork(){ return BrowseFileFolderNetwork; }
    public static String GetLaunchExternalApplication(){ return LaunchExternalApplication; }
    public static String GetDynamicList(){ return DynamicList; }
    
        
    public static String GetButtonText(String Type){
        return ActionList.get(Type).ButtonText;
    }
    
    public static String GetFieldTitle(String Type){
        return ActionList.get(Type).FieldTitle;
    }
    
    public static String GetWidgetSymbol(String Type){
        return ActionList.get(Type).WidgetSymbol;
    }
    
    public static Boolean GetAdvancedOnly(String Type){
        return ActionList.get(Type).AdvancedOnly;
    }
    
    public static Boolean GetDiamondOnly(String Type){
        return ActionList.get(Type).DiamondOnly;
    }
    
    public static List<ActionVariable> GetActionVariables(String Type){
        return ActionList.get(Type).ActionVariables;
    }
    
    public static Collection<String> GetTypes(){
        if (util.IsAdvancedMode() && Diamond.IsDiamond()){
            return ActionList.keySet();
        }else{
            Collection<String> tempList = new LinkedHashSet<String>();
            for (String Item : ActionList.keySet()){
                if (GetAdvancedOnly(Item)){
                    if (util.IsAdvancedMode()){
                        tempList.add(Item);
                    }
                }else if (GetDiamondOnly(Item)){
                    if (Diamond.IsDiamond()){
                        tempList.add(Item);
                    }
                }else{
                    tempList.add(Item);
                }
            }
            return tempList;
        }
    }

    public static Boolean IsValidAction(String Type){
        if (Type.equals(ActionTypeDefault)){
            System.out.println("ADM: aIsValidAction - FALSE for = '" + Type + "'");
            return Boolean.FALSE;
        }else{
            System.out.println("ADM: aIsValidAction - Lookup for = '" + Type + "' = '" + ActionList.containsKey(Type) + "'");
            return ActionList.containsKey(Type);
        }
    }
    
    public static String GetAttributeButtonText(String Type, String Attribute){
        return GetAttributeButtonText(Type, Attribute, Boolean.FALSE);
    }
    
    public static String GetAttributeButtonText(String Type, String Attribute, Boolean IgnoreAdvanced){
        if (Type.equals(StandardMenuAction)){
            //determine if using Advanced options
            if (util.IsAdvancedMode() && !IgnoreAdvanced){
                return SageMenuActions.get(Attribute).ButtonText + " (" + Attribute + ")";
            }else{
                return SageMenuActions.get(Attribute).ButtonText;
            }
        }else if(Type.equals(WidgetbySymbol)){
            return Attribute + " - " + GetWidgetName(Attribute);
        }else if(Type.equals(BrowseVideoFolder)){
            if (Attribute==null){
                return "Root";
            }else{
                return Attribute;
            }
        }else if(Type.equals(TVRecordingView)){
            return GetSageTVRecordingViewsButtonText(Attribute);
        }else if(Type.equals(DiamondDefaultFlows)){
            if (util.IsAdvancedMode() && !IgnoreAdvanced){
                return Diamond.DiamondDefaultFlows.get(Attribute).ButtonText + " (" + Attribute + ")";
            }else{
                return Diamond.DiamondDefaultFlows.get(Attribute).ButtonText;
            }
        }else if(Type.equals(DynamicList)){
            return DynamicLists.get(Attribute);
        }else if(Type.equals(DiamondCustomFlows)){
            return Diamond.GetViewName(Attribute);
        }else if(Type.equals(LaunchExternalApplication)){
            if (Attribute.isEmpty()){
                return "Configure";
            }else{
                return "Configure (" + Attribute + ")";
            }
        }else if(IsFileBrowserType(Type)){
            if (Attribute==null){
                return "Choose";
            }else{
                return Attribute;
            }
        }else{
            return util.OptionNotFound;
        }
    }
    
    //execute the Action based on the Menu Item ActionType value
    public static void Execute(String MenuItemName){
        String tActionType = MenuNode.GetMenuItemActionType(MenuItemName);
        String tActionAttribute = MenuNode.GetMenuItemAction(MenuItemName);
        System.out.println("ADM: aExecute - ActionType = '" + tActionType + "' Action = '" + tActionAttribute + "'");
        if (!tActionType.equals(ActionTypeDefault)){
            if (tActionType.equals(StandardMenuAction)){
                SageMenuActions.get(tActionAttribute).Execute(tActionAttribute);
            }else{
                //see if there are any ActionVariables that need to be evaluated
                for (ActionVariable tActionVar : GetActionVariables(tActionType)){
                    tActionVar.EvaluateVariable(tActionAttribute);
                }
                //determine what to execute
                if (tActionType.equals(LaunchExternalApplication)){
                    //launch external application
                    ExternalAction tExtApp = MenuNode.GetMenuItemActionExternal(MenuItemName);
                    tExtApp.Execute();
                }else{
                    //either execute the default widget symbol or the one for the Menu Item passed in
                    if (GetWidgetSymbol(tActionType).equals(Blank)){
                        ExecuteWidget(tActionAttribute);
                    }else{
                        ExecuteWidget(GetWidgetSymbol(tActionType));
                    }
                }
            }
        }
        //else do nothing
    }
    
    public static Boolean ExecuteWidget(String WidgetSymbol){
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(new UIContext(sagex.api.Global.GetUIContextName()), WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: aExecuteWidget - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
            return Boolean.FALSE;
        }else{
            System.out.println("ADM: aExecuteWidget - ExecuteWidgetChain called with WidgetSymbol = '" + WidgetSymbol + "'");

            try {
                sage.SageTV.apiUI(new UIContext(sagex.api.Global.GetUIContextName()).toString(), "ExecuteWidgetChainInCurrentMenuContext", passvalue);
            } catch (InvocationTargetException ex) {
                System.out.println("ADM: aExecuteWidget: error executing widget" + util.class.getName() + ex);
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
            //            sagex.api.WidgetAPI.ExecuteWidgetChain(MyUIContext, WidgetSymbol);
            
        }
               
    }

    public static Boolean IsWidgetValid(String WidgetSymbol){
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(new UIContext(sagex.api.Global.GetUIContextName()), WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: aIsWidgetValid - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
            return Boolean.FALSE;
        }else{
            System.out.println("ADM: aIsWidgetValid - FindWidgetSymbol passed for WidgetSymbol = '" + WidgetSymbol + "'");
            return Boolean.TRUE;
        }
               
    }
    
    public static String GetWidgetName(String WidgetSymbol){
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(new UIContext(sagex.api.Global.GetUIContextName()), WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: aGetWidgetName - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
            return util.OptionNotFound;
        }else{
            String WidgetName = sagex.api.WidgetAPI.GetWidgetName(new UIContext(sagex.api.Global.GetUIContextName()), WidgetSymbol);
            System.out.println("ADM: aGetWidgetName for Symbol = '" + WidgetSymbol + "' = '" + WidgetName + "'");
            return WidgetName;
        }
               
    }
    
    public static void LoadStandardActionList(){
        Properties StandardActionProps = new Properties();
        String StandardActionPropsPath = util.GetADMDefaultsLocation() + File.separator + StandardActionListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(StandardActionPropsPath);
            try {
                StandardActionProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: aLoadStandardActionList: IO exception loading standard actions " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: aLoadStandardActionList: file not found loading standard actions " + util.class.getName() + ex);
            return;
        }

        //Add all the Actions as Custom Actions
        for (String ActionItem : StandardActionProps.stringPropertyNames()){
            CustomAction tAction = new CustomAction(ActionItem, StandardActionProps.getProperty(ActionItem),ActionItem);
            SageMenuActions.put(ActionItem, tAction);
        }

        System.out.println("ADM: aLoadStandardActionList: completed for '" + StandardActionPropsPath + "'");
        return;
    }

    public static void LoadDynamicLists(){
        //Dynamic Lists are single menu items that expand themselves into a list of items of a specified type
        DynamicLists.clear();
        DynamicLists.put(DynamicTVRecordingsList, "TV Recordings List");
        DynamicLists.put(DynamicVideoPlaylist, "Video Playlist");
    }
    
    public static Collection<String> GetDynamicListItems(String Attribute){
        if (Attribute.equals(DynamicTVRecordingsList)){
            return SageTVRecordingViews.keySet();
        }else if(Attribute.equals(DynamicVideoPlaylist)){
            return SageTVRecordingViews.keySet();
        }else{
            return Collections.emptyList();
        }
    }

    public static Collection<String> GetActionList(String Type){
        //TODO: build a AllActions list to provide a search or full list to select from.
        if (Type.equals(StandardMenuAction)){
            return CustomAction.ActionListSorted.values();
        }else if(Type.equals(TVRecordingView)){
            return SageTVRecordingViews.keySet();
        }else if(Type.equals(DynamicList)){
            return DynamicLists.keySet();
        }else if(Type.equals(DiamondDefaultFlows)){
            return Diamond.DefaultFlow.ListSorted.values();
        }else if(Type.equals(DiamondCustomFlows)){
            return Diamond.GetCustomViews();
        }else{
            return Collections.emptyList();
        }
    }

    public static Boolean HasActionList(String Type){
        if (Type.equals(StandardMenuAction)){
            return Boolean.TRUE;
        }else if(Type.equals(TVRecordingView)){
            return Boolean.TRUE;
        }else if(Type.equals(DynamicList)){
            return Boolean.TRUE;
        }else if(Type.equals(DiamondDefaultFlows)){
            return Boolean.TRUE;
        }else if(Type.equals(DiamondCustomFlows)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public static Boolean UseGenericEditBox(String Type){
        if (Type.equals(BrowseVideoFolder)){
            return Boolean.TRUE;
        }else if(IsFileBrowserType(Type)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public static Boolean IsFileBrowserType(String Type){
        if (Type.equals(BrowseFileFolderLocal)){
            return Boolean.TRUE;
        }else if(Type.equals(BrowseFileFolderServer)){
            return Boolean.TRUE;
        }else if(Type.equals(BrowseFileFolderImports)){
            return Boolean.TRUE;
        }else if(Type.equals(BrowseFileFolderNetwork)){
            return Boolean.TRUE;
        }else if(Type.equals(BrowseFileFolderRecDir)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }

    public static String GetFileBrowserType(String Style){
        if (Style.equals("xLocal")){
            return BrowseFileFolderLocal;
        }else if(Style.equals("xServer")){
            return BrowseFileFolderServer;
        }else if(Style.equals("xImports")){
            return BrowseFileFolderImports;
        }else if(Style.equals("xRecDirs")){
            return BrowseFileFolderRecDir;
        }else if(Style.equals("xNetwork")){
            return BrowseFileFolderNetwork;
        }else{
            return util.OptionNotFound;
        }
    }

    public static String GetGenericEditBoxMessage(String Type){
        if (Type.equals(BrowseVideoFolder)){
            return "Enter a Folder Name/Path:\nHint: use the same text as displayed\nin the 'Video by Folder' view next to 'Folder (case sensitive):'";
        }else if(IsFileBrowserType(Type)){
            return "Enter a Folder Name/Path:\nHint: use the same text as displayed\nin the File Browser";
        }else if(Type.equals(LaunchExternalApplication)){
            return "Enter a command";
        }else{
            return "";
        }
    }

    private static void LoadSageCustomMenuActions(){
        Properties CustomActionProps = new Properties();
        String CustomActionPropsPath = util.GetADMDefaultsLocation() + File.separator + CustomActionListFile;
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(CustomActionPropsPath);
            try {
                CustomActionProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: aLoadSageCustomMenuActions: IO exception loading custom actions " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: aLoadSageCustomMenuActions: file not found loading custom actions " + util.class.getName() + ex);
            return;
        }

        //write all the custom actions to Sage properties as it is easier to parse them that way - delete them when done
        if (CustomActionProps.size()>0){
            //clean up existing Custom Actions from the SageTV properties file before writing the new ones
            util.RemovePropertyAndChildren(SageADMCustomActionsPropertyLocation);
            
            for (String tPropertyKey : CustomActionProps.stringPropertyNames()){
                util.SetProperty(tPropertyKey, CustomActionProps.getProperty(tPropertyKey));
            }
        }
        
        //load custom menu actions from Sage Properties
        //find all Custom Action Name entries from the SageTV properties file
        String[] CustomActionNames = sagex.api.Configuration.GetSubpropertiesThatAreBranches(new UIContext(sagex.api.Global.GetUIContextName()),SageADMCustomActionsPropertyLocation);
        
        if (CustomActionNames.length>0){
            String PropLocation = "";
            for (String tCustomActionName : CustomActionNames){
                System.out.println("ADM: aLoadSageCustomMenuActions: loading '" + tCustomActionName + "' Custom Menu Action");
                PropLocation = SageADMCustomActionsPropertyLocation + "/" + tCustomActionName;
                String tButtonText = util.GetProperty(PropLocation + "/ButtonText", util.ButtonTextDefault);
                String tWidgetSymbol = util.GetProperty(PropLocation + "/WidgetSymbol", "");
                String tCopyModeAttributeVar = util.GetProperty(PropLocation + "/CopyModeAttributeVar", Blank);
                CustomAction tAction = new CustomAction(tCustomActionName,tButtonText, tWidgetSymbol, tCopyModeAttributeVar);
                SageMenuActions.put(tCustomActionName,tAction);
                Integer Counter = 0;
                Boolean Found = Boolean.TRUE;
                do {
                    Counter++;
                    //first test if the current action variable is available
                    String AVPropLocation = PropLocation + "/ActionVariables/" + Counter;
                    if (util.HasProperty(AVPropLocation + "/VarType")){
                        ActionVariable tVar = new ActionVariable();
                        tVar.VarType = util.GetProperty(AVPropLocation + "/VarType", VarTypeGlobal);
                        tVar.Var = util.GetProperty(AVPropLocation + "/Var", "");
                        tVar.Val = util.GetProperty(AVPropLocation + "/Val", "");
                        SageMenuActions.get(tCustomActionName).ActionVariables.add(tVar);
                        System.out.println("ADM: aLoadSageCustomMenuActions: Loading Vars from '" + AVPropLocation + "' VarType='" + tVar.VarType + "' Var='" + tVar.Var + "' Val ='" + tVar.Val + "'");
                    }else{
                        Found = Boolean.FALSE;
                    }
                } while (Found);
            }
        }
        
        //clean up existing Custom Actions from the SageTV properties file as they are no longer needed
        util.RemovePropertyAndChildren(SageADMCustomActionsPropertyLocation);
        System.out.println("ADM: aLoadSageCustomMenuActions: completed loading '" + SageMenuActions.size() + "' Custom Menu Actions");
    }

    private static void LoadSageTVRecordingViews(){
        SageTVRecordingViews.clear();
        //put the ViewType and Default View Name into a list
        SageTVRecordingViews.put("xAll","All Recordings");
        SageTVRecordingViews.put("xRecordings","Archived Recordings");
        SageTVRecordingViews.put("xArchives","Recorded Movies");
        SageTVRecordingViews.put("xMovies","Current Recordings");
        //SageTVRecordingViews.put("xPartials","");
        SageTVRecordingViews.put("xView5","Recording View5");
        SageTVRecordingViews.put("xView6","Recording View6");
        SageTVRecordingViews.put("xView7","Recording View7");
        SageTVRecordingViews.put("xView8","Recording View8");
    }

    public static String GetSageTVRecordingViewsButtonText(String Name){
        //return the stored name from Sage or the Default Name if nothing is stored
        return util.GetProperty(SageTVRecordingViewsTitlePropertyLocation + Name, SageTVRecordingViews.get(Name));
    }
    
    public static void SetSageTVRecordingViewsButtonText(String ViewType, String Name){
        //rename the specified TV Recording View 
        util.SetProperty(SageTVRecordingViewsTitlePropertyLocation + ViewType, Name);
    }

    public static class ActionVariable{
        private String Var = "";
        private String Val = "";
        private String VarType = VarTypeGlobal;

        public ActionVariable(){
        }
        
        public ActionVariable(String VarType, String Var, String Val ){
            this.VarType = VarType;
            this.Var = Var;
            this.Val = Val;
        }
        
        public String getVarType(){
            return this.VarType;
        }
        public String getVar(){
            return this.Var;
        }
        public String getVal(){
            return this.Val;
        }
        public void EvaluateVariable(String Attribute){
            String tVal = this.Val;
            if (this.Val.equals(UseAttributeValue)){
                tVal = Attribute;
            }else if (this.Val.equals(VarNull)){
                tVal = null;
            }
            if (this.VarType.equals(VarTypeGlobal)){
                sagex.api.Global.AddGlobalContext(new UIContext(sagex.api.Global.GetUIContextName()), this.Var, tVal);
            }else if (this.VarType.equals(VarTypeStatic)){
                sagex.api.Global.AddStaticContext(new UIContext(sagex.api.Global.GetUIContextName()), this.Var, tVal);
            }else if (this.VarType.equals(VarTypeSetProp)){
                util.SetProperty(this.Var, tVal);
            }
            System.out.println("ADM: aEvaluateVariable - Type '" + this.VarType + "' setting '" + this.Var + "' to '" + tVal + "' for Attribute '" + Attribute + "' original Val ='" + this.Val + "'");
        }
    }

    public static class CustomAction{
        private String Name = "";
        private String ButtonText = "";
        private String WidgetSymbol = "";
        private String CopyModeAttributeVar = Blank;
        private List<ActionVariable> ActionVariables = new LinkedList<ActionVariable>();
        public static Collection<String> WidgetSymbols = new LinkedHashSet<String>();
        //the combination of the Name and CopymodeAttributeVar fields make the CustomAction unique for the CopyMode
        public static Collection<String> CopyModeUniqueIDs = new LinkedHashSet<String>();
        //need a list of keys that are sorted by the ButtonText
        public static SortedMap<String,String> ActionListSorted = new TreeMap<String,String>();

        
//        public CustomAction(String Name, String ButtonText){
//            this(Name,ButtonText,"",Blank);
//        }
//
        public CustomAction(String Name, String ButtonText, String WidgetSymbol){
            this(Name,ButtonText,WidgetSymbol,Blank);
        }

        public CustomAction(String Name, String ButtonText, String WidgetSymbol, String CopyModeAttributeVar){
            this.Name = Name;
            this.ButtonText = ButtonText;
            this.WidgetSymbol = WidgetSymbol;
            this.CopyModeAttributeVar = CopyModeAttributeVar;
            //WidgetSymbols list is used for the copymode function to further refine the item to be copied
            //therefore it is not populated for items that do not have a CopyModeAttributeVar
            if (!CopyModeAttributeVar.equals(Blank)){
                WidgetSymbols.add(WidgetSymbol);
            }
            CopyModeUniqueIDs.add(UniqueID(CopyModeAttributeVar,Name));
            ActionListSorted.put(this.ButtonText, Name);
        }
        
        public void Execute(String Attribute){
            //evaluate all the ActionVariables first
            for (ActionVariable ActionVar : ActionVariables){
                ActionVar.EvaluateVariable(Attribute);
            }
            
            //Execute the WidgetSymbol
            Action.ExecuteWidget(WidgetSymbol);
        }
        
        public static String UniqueID(String Name, String Var){
            return Name + util.ListToken + Var;
        }
        
    }
    
    public static class ExternalAction{
        //portions borrowed from NielM ExtCommand.java code
        private Integer windowType; // (maximised | minimised | hidden | normal | console )
        private String command;
        private String arguments;
        private Boolean waitForExit;
        private Integer sageStatus;
        private String MenuItemName;

        static public final String[] windowTypeStrings = { 
            "Normal",
            "Maximised",
            "Minimised",
            "Hidden"
        };
        static public final int WINDOW_NORMAL =0;
        static public final int WINDOW_MAXIMISED=1;
        static public final int WINDOW_MINIMISED=2;
        static public final int WINDOW_HIDDEN=3;

        static public final String[] sageStatusStrings = { 
            "Do nothing with Sage",
            "Put Sage in Sleep Mode",
            "Exit Sage after application launch"
        };
        static public final Integer SageStatusNothing = 0;
        static public final Integer SageStatusSleep = 1;
        static public final Integer SageStatusExit = 2;
        
        public ExternalAction(String MenuItemName){
            this.MenuItemName = MenuItemName;
            this.command="";
            this.windowType=0;
            this.arguments="";
            this.waitForExit=Boolean.TRUE;
            this.sageStatus = SageStatusNothing;
        }
        
        public ExternalAction(String MenuItemName, String command, Integer windowType, String arguments, Boolean waitForExit, Integer sageStatus ){
            this.MenuItemName = MenuItemName;
            this.command=command;
            
            //validate windowType
            if (windowType<0 || windowType>windowTypeStrings.length){
                this.windowType=0;  //default to Normal
            }else{
                this.windowType=windowType;
            }
            this.arguments=arguments;
            this.waitForExit=waitForExit;
            if (sageStatus<0 || sageStatus>sageStatusStrings.length){
                this.sageStatus = SageStatusNothing;
            }else{
                this.sageStatus = sageStatus;
            }
        }
        
        public String GetApplication(){
            return this.command;
        }
        public void SetApplication(String bApplication){
            this.command = bApplication;
            this.Save();
        }
        public String GetArguments(){
            return this.arguments;
        }
        public void SetArguments(String bArguments){
            this.arguments = bArguments;
            this.Save();
        }
        public String GetWindowType(){
            return windowTypeStrings[windowType];
        }
        public void ChangeWindowType(Integer Delta){
            this.windowType = this.windowType + Delta;
            if (windowType>=windowTypeStrings.length){
                this.windowType = 0;
            }else if(windowType<0){
                this.windowType = windowTypeStrings.length-1;
            }
            this.Save();
        }
        
        public String GetWaitForExit(){
            if (waitForExit){
                return "Wait until Application Exits";
            }else{
                return "Do not wait";
            }
        }
        public void ChangeWaitForExit(){
            this.waitForExit = !this.waitForExit;
            this.Save();
        }

        public String GetSageStatus(){
            return sageStatusStrings[sageStatus];
        }
        public void ChangeSageStatus(Integer Delta){
            this.sageStatus = this.sageStatus + Delta;
            if (sageStatus>=sageStatusStrings.length){
                this.sageStatus = 0;
            }else if(sageStatus<0){
                this.sageStatus = sageStatusStrings.length-1;
            }
            this.Save();
        }
        
        public void Save(){
            //save all variables to the sage Properties
            String PropLocation = util.SagePropertyLocation + MenuItemName + "/ExternalAction/";
            util.SetProperty(PropLocation + "Application", command);
            util.SetProperty(PropLocation + "Arguments", arguments);
            util.SetProperty(PropLocation + "WindowType", windowType.toString());
            util.SetProperty(PropLocation + "WaitForExit", waitForExit.toString());
            util.SetProperty(PropLocation + "SageStatus", sageStatus.toString());
        }
        
        public void Load(){
            //save all variables to the sage Properties
            String PropLocation = util.SagePropertyLocation + MenuItemName + "/ExternalAction/";
            this.command = util.GetProperty(PropLocation + "Application", "");
            this.arguments = util.GetProperty(PropLocation + "Arguments", "");
            this.windowType = util.GetPropertyAsInteger(PropLocation + "WindowType", 0);
            this.waitForExit = util.GetPropertyAsBoolean(PropLocation + "WaitForExit", Boolean.TRUE);
            this.sageStatus = util.GetPropertyAsInteger(PropLocation + "SageStatus", 0);
        }

        public void AddProperties(Properties inProp){
            String PropLocation = util.SagePropertyLocation + MenuItemName + "/ExternalAction/";
            inProp.setProperty(PropLocation + "Application", command);
            inProp.setProperty(PropLocation + "Arguments", arguments);
            inProp.setProperty(PropLocation + "WindowType", windowType.toString());
            inProp.setProperty(PropLocation + "WaitForExit", waitForExit.toString());
            inProp.setProperty(PropLocation + "SageStatus", sageStatus.toString());
        }
        
        public void Execute(){
            UIContext MyUIContext = new UIContext(sagex.api.Global.GetUIContextName());
            try{            
                String osName = System.getProperty("os.name");
                String[] cmd = null;

                if ( osName.toLowerCase().startsWith("windows")) {
                    cmd = new String[3];
                    if( osName.equals( "Windows 95" ) || osName.equals( "Windows 98" ) ){
                        cmd[0] = "command.com" ;
                        cmd[1] = "/C" ;
                    } else if( osName.toLowerCase().contains("win")){//any newer Windows OS
                        cmd[0] = "cmd.exe" ;
                        cmd[1] = "/C" ;
                    }else {
                        System.out.println("ADM: aExternalAction.Execute: unknown OS:"+osName+" assuming newer Windows OS");
                        cmd[0] = "cmd.exe" ;
                        cmd[1] = "/C" ;
                    }
                    cmd[2] = "start \"console\" ";
                    if ( waitForExit ){
                        cmd[2]=cmd[2] + "/wait ";
                    }
                    //Window Types
                    if ( windowType == WINDOW_MAXIMISED ){
                        cmd[2] = cmd[2] +"/max ";
                    }else if ( windowType == WINDOW_MINIMISED ){
                        cmd[2] = cmd[2] +"/min ";
                    }else if ( windowType == WINDOW_HIDDEN ){
                        cmd[2] = cmd[2] +"/b ";
                    }

                    // append command -- first for window title, second for exe name
                    cmd[2]=cmd[2]+"\""+command+"\" " + arguments;
                    System.out.println("ADM: aExternalAction.Execute: Command = '" + cmd[0] + " " + cmd[1] + " " + cmd[2] + "'");
                } else {
                    cmd = new String[2];
                    cmd[0]=command;
                    cmd[1]=arguments;
                    System.out.println("ADM: aExternalAction.Execute: for unknown OS '" + osName + "' Command = '" + cmd[0] + " " + cmd[1] + "'" );

                }
                //determine what to do with Sage - before
                Boolean tFullScreen = sagex.api.Global.IsFullScreen(MyUIContext);
                if (sageStatus.equals(SageStatusSleep)){
                    if (tFullScreen){
                        sagex.api.Global.SetFullScreen(MyUIContext, Boolean.FALSE);
                    }
                    sagex.api.Global.SageCommand(MyUIContext,"Power Off");
                }else if (sageStatus.equals(SageStatusExit)){
                    //the exit command would only occur AFTER the External Application is executed - see below
                    //sagex.api.Global.Exit(new UIContext(sagex.api.Global.GetUIContextName()));
                }else{
                    //do thing assumed
                }
                
                Runtime rt = Runtime.getRuntime();
                Process proc = rt.exec(cmd);
                // any error message?
                StreamGobbler errorGobbler = new StreamGobbler(proc.getErrorStream(), "ERROR");            

                // any output?
                StreamGobbler outputGobbler = new StreamGobbler(proc.getInputStream(), "OUTPUT");

                // kick them off
                errorGobbler.start();
                outputGobbler.start();

                // any error???
                int exitVal = proc.waitFor();
                System.out.println("ADM: aExternalAction.Execute: ExitValue: '" + exitVal + "'"); 

                //determine what to do with Sage - after
                if (sageStatus.equals(SageStatusSleep)){
                    sagex.api.Global.SageCommand(MyUIContext,"Power On");
                    if (tFullScreen){
                        sagex.api.Global.SetFullScreen(MyUIContext, Boolean.TRUE);
                    }
                }else if (sageStatus.equals(SageStatusExit)){
                    //exit Sage after starting the External Application
                    sagex.api.Global.Exit(new UIContext(sagex.api.Global.GetUIContextName()));
                }else{
                    //do thing assumed
                }
                
                
            } catch (Throwable t)
            {
                System.out.println("ADM: aExternalAction.Execute: ERROR - Exception = '" + t + "'"); 
                //t.printStackTrace();
            }
            
        }
        
        //StreamGobbler class from
        //http://www.javaworld.com/javaworld/jw-12-2000/jw-1229-traps.html

        public static class StreamGobbler extends Thread
        {
            InputStream is;
            String type;

            StreamGobbler(InputStream is, String type)
            {
                this.is = is;
                this.type = type;
            }

            @Override
            public void run()
            {
                try
                {
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String line=null;
                    while ( (line = br.readLine()) != null)
                        System.out.println(type + ">" + line);
                } catch (IOException ioe)
                {
                    System.out.println("ADM: aExternalAction.StreamGobbler: ERROR - Exception = '" + ioe + "'"); 
                    //ioe.printStackTrace();
                }
            }
        }

    }
    
    
}
