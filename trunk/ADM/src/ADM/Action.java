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
    private static final String UseAttributeValue = "admActionUseAttributeValue";
    private static final String StandardActionListFile = "ADMStandardActions.properties";
    private static final String DiamondDefaultFlowsListFile = "ADMDiamondDefaultFlows.properties";
    public static Properties StandardActionProps = new Properties();
    public static Collection<String> StandardActionKeys = new LinkedHashSet<String>();
    public static Properties DiamondDefaultFlowsProps = new Properties();
    public static Collection<String> DiamondDefaultFlowsKeys = new LinkedHashSet<String>();
    public static Map<String,String>  SageTVRecordingViews = new LinkedHashMap<String,String>();
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
    private static final String VarTypeSetProp = "VarTypeSetProp";
    private Boolean AdvancedOnly = Boolean.FALSE;
    private Boolean DiamondOnly = Boolean.FALSE;
    private static Map<String,Action> ActionList = new LinkedHashMap<String,Action>();
    private static Boolean InitComplete = Boolean.FALSE;
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
    public static final String ActionTypeDefault = "DoNothing";

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
        if (!InitComplete){
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
            LoadStandardActionList();
            LoadDiamondDefaultFlowsList();

            //load the SageTV Recording views
            LoadSageTVRecordingViews();
            
            InitComplete = Boolean.TRUE;
        }
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
    
//    public static String GetAttribute(String Type){
//        return ActionList.get(Type).Attribute;
//    }
//    
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
        if (Type.equals(StandardMenuAction)){
            //determine if using Advanced options
            if (util.IsAdvancedMode()){
                return StandardActionProps.getProperty(Attribute, util.OptionNotFound) + " (" + Attribute + ")";
            }else{
                return StandardActionProps.getProperty(Attribute, util.OptionNotFound);
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
            if (util.IsAdvancedMode()){
                return DiamondDefaultFlowsProps.getProperty(Attribute, util.OptionNotFound) + " (" + Attribute + ")";
            }else{
                return DiamondDefaultFlowsProps.getProperty(Attribute, util.OptionNotFound);
            }
        }else if(Type.equals(DiamondCustomFlows)){
            return Diamond.GetViewName(Attribute);
        }else if(Type.equals(LaunchExternalApplication)){
            return "Configure Settings";
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
            //see if there are any ActionVariables that need to be evaluated
            for (ActionVariable tActionVar : GetActionVariables(tActionType)){
                tActionVar.EvaluateVariable(tActionAttribute);
            }
            //determine what to execute
            if (tActionType.equals(LaunchExternalApplication)){
                //launch external application
                ExternalAction tExtApp = new ExternalAction(tActionAttribute, 0, "", Boolean.FALSE, ExternalAction.SageStatusNothing);
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
    
    public static void LoadDiamondDefaultFlowsList(){
        String DiamondDefaultFlowsPropsPath = util.GetADMDefaultsLocation() + File.separator + DiamondDefaultFlowsListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(DiamondDefaultFlowsPropsPath);
            try {
                DiamondDefaultFlowsProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: aLoadDiamondDefaultFlowsList: IO exception loading DiamondDefaultFlows " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: aLoadDiamondDefaultFlowsList: file not found loading DiamondDefaultFlows " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> ActionValuesList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String ActionItem : DiamondDefaultFlowsProps.stringPropertyNames()){
            ActionValuesList.put(DiamondDefaultFlowsProps.getProperty(ActionItem),ActionItem);
        }

        //build a list of keys in the order of the values
        for (String ActionValue : ActionValuesList.keySet()){
            DiamondDefaultFlowsKeys.add(ActionValuesList.get(ActionValue));
        }
        
        System.out.println("ADM: aLoadDiamondDefaultFlowsList: completed for '" + DiamondDefaultFlowsPropsPath + "'");
        return;
    }

    public static void LoadStandardActionList(){
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

        //sort the keys into value order
        SortedMap<String,String> ActionValuesList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String ActionItem : StandardActionProps.stringPropertyNames()){
            ActionValuesList.put(StandardActionProps.getProperty(ActionItem),ActionItem);
        }

        //build a list of keys in the order of the values
        for (String ActionValue : ActionValuesList.keySet()){
            StandardActionKeys.add(ActionValuesList.get(ActionValue));
        }
        
        System.out.println("ADM: aLoadStandardActionList: completed for '" + StandardActionPropsPath + "'");
        return;
    }

    public static Collection<String> GetActionList(String Type){
        if (Type.equals(StandardMenuAction)){
            return StandardActionKeys;
        }else if(Type.equals(TVRecordingView)){
            return SageTVRecordingViews.keySet();
        }else if(Type.equals(DiamondDefaultFlows)){
            return DiamondDefaultFlowsKeys;
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
        }else if(Type.equals(LaunchExternalApplication)){
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

    private static void LoadSageTVRecordingViews(){
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
            }
            if (this.VarType.equals(VarTypeGlobal)){
                sagex.api.Global.AddStaticContext(new UIContext(sagex.api.Global.GetUIContextName()), this.Var, tVal);
                System.out.println("ADM: aEvaluateVariable - Setting Static Context for = '" + this.Var + "' to '" + tVal + "'");
            }else if (this.VarType.equals(VarTypeSetProp)){
                util.SetProperty(this.Var, tVal);
                System.out.println("ADM: aEvaluateVariable - Setting Property = '" + this.Var + "' to '" + tVal + "'");
            }
        }
    }
    
    public static class ExternalAction{
        //portions borrowed from NielM ExtCommand.java code
        private Integer windowType; // (maximised | minimised | hidden | normal | console )
        private String command;
        private String arguments;
        private Boolean waitForExit;
        private String sageStatus;

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

        static public final String SageStatusExit = "SageStatusExit";
        static public final String SageStatusSleep = "SageStatusSleep";
        static public final String SageStatusNothing = "SageStatusNothing";
        
        public ExternalAction(){
            this.command="";
            this.windowType=0;
            this.arguments="";
            this.waitForExit=Boolean.FALSE;
            this.sageStatus = SageStatusNothing;
        }
        
        public ExternalAction(String command, Integer windowType, String arguments, Boolean waitForExit, String sageStatus ){
            this.command=command;
            
            //validate windowType
            if (windowType<0 || windowType>3){
                this.windowType=0;  //default to Normal
            }else{
                this.windowType=windowType;
            }
            this.arguments=arguments;
            this.waitForExit=waitForExit;
            this.sageStatus = sageStatus;
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
