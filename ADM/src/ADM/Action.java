/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        this.Attribute = Attribute;
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
            
            ActionList.put(BrowseFileFolderLocal, new Action(BrowseFileFolderLocal,Boolean.FALSE,Boolean.FALSE,"File Browser: Local Folder","Local File Folder","BASE-51703"));
            ActionList.get(BrowseFileFolderLocal).ActionVariables.add(new ActionVariable(VarTypeGlobal,"ForceReload", "true"));
            ActionList.get(BrowseFileFolderLocal).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_style", "xLocal"));
            ActionList.get(BrowseFileFolderLocal).ActionVariables.add(new ActionVariable(VarTypeSetProp,"file_browser/last_folder/local", UseAttributeValue));

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
    
    public static String GetAttribute(String Type){
        return ActionList.get(Type).Attribute;
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
        }else if(Type.equals(BrowseFileFolderLocal)){
            if (Attribute==null){
                return "Choose";
            }else{
                return Attribute;
            }
        }else{
            return util.OptionNotFound;
        }
    }
    
    
    public static void Execute(String ActionType, String ActionAttribute){
        System.out.println("ADM: aExecute - ActionType = '" + ActionType + "' Action = '" + ActionAttribute + "'");
        if (!ActionType.equals(ActionTypeDefault)){
//            if (!GetAttribute(ActionType).equals(Blank)){
//                //Set a Static Context for the Attribute to the ActionAttribute
//                System.out.println("ADM: aExecute - Setting Static Context for = '" + GetAttribute(ActionType) + "' to '" + ActionAttribute + "'");
//                sagex.api.Global.AddStaticContext(new UIContext(sagex.api.Global.GetUIContextName()), GetAttribute(ActionType), ActionAttribute);
//            }
            //see if there are any ActionVariables that need to be evaluated
            for (ActionVariable tActionVar : GetActionVariables(ActionType)){
                tActionVar.EvaluateVariable(ActionAttribute);
            }
            //either execute the default widget symbol or the one passed in
            if (GetWidgetSymbol(ActionType).equals(Blank)){
                ExecuteWidget(ActionAttribute);
            }else{
                ExecuteWidget(GetWidgetSymbol(ActionType));
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
        }else if(Type.equals(WidgetbySymbol)){
            return Boolean.FALSE;
        }else if(Type.equals(BrowseVideoFolder)){
            return Boolean.FALSE;
        }else if(Type.equals(TVRecordingView)){
            return Boolean.TRUE;
        }else if(Type.equals(DiamondDefaultFlows)){
            return Boolean.TRUE;
        }else if(Type.equals(DiamondCustomFlows)){
            return Boolean.TRUE;
        }else if(Type.equals(BrowseFileFolderLocal)){
            return Boolean.FALSE;
        }else{
            return Boolean.FALSE;
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
    
}
