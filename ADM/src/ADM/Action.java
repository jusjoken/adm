/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import sagex.UIContext;

/**
 *
 * @author jusjoken
 */
public class Action {

    private static final String Blank = "admActionBlank";
    private static final UIContext MyUIContext = new UIContext(sagex.api.Global.GetUIContextName());
    private String Type = "";
    private String ButtonText = "";
    private String Attribute = Blank;
    private String WidgetSymbol = Blank;
    private String FieldTitle = "";
    private static Map<String,Action> ActionList = new LinkedHashMap<String,Action>();
    private static Boolean InitComplete = Boolean.FALSE;
    public static final String WidgetbySymbol = "ExecuteWidget";
    public static final String BrowseVideoFolder = "ExecuteBrowseVideoFolder";
    public static final String StandardMenuAction = "ExecuteStandardMenuAction";
    public static final String TVRecordingView = "ExecuteTVRecordingView";
    public static final String ActionTypeDefault = "DoNothing";

    public Action(String Type, String ButtonText){
        this(Type,ButtonText,"Action",Blank,Blank);
    }

    public Action(String Type, String ButtonText, String FieldTitle){
        this(Type,ButtonText,FieldTitle,Blank,Blank);
    }

    public Action(String Type, String ButtonText, String FieldTitle, String WidgetSymbol){
        this(Type,ButtonText,FieldTitle,WidgetSymbol,Blank);
    }

    public Action(String Type, String ButtonText, String FieldTitle, String WidgetSymbol, String Attribute){
        this.Type = Type;
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
            ActionList.put(ActionTypeDefault, new Action(ActionTypeDefault,"None"));
            ActionList.put(WidgetbySymbol, new Action(WidgetbySymbol,"Execute Widget by Symbol", "Action"));
            ActionList.put(BrowseVideoFolder, new Action(BrowseVideoFolder,"Video Browser with specific Folder","Video Browser Folder","OPUS4A-174637","gCurrentVideoBrowserFolder"));
            ActionList.put(StandardMenuAction, new Action(StandardMenuAction,"Execute Standard Sage Menu Action", "Standard Action"));
            ActionList.put(TVRecordingView, new Action(TVRecordingView,"Launch Specific TV Recordings View", "TV Recordings View","OPUS4A-174116", "ViewFilter"));
            InitComplete = Boolean.TRUE;
        }
    }
    
    public static String GetButtonText(String Type){
        return ActionList.get(Type).ButtonText;
    }
    
    public static String GetFieldTitle(String Type){
        return ActionList.get(Type).FieldTitle;
    }
    
    public static String GetWidgetSymbol(String Type){
        return ActionList.get(Type).WidgetSymbol;
    }
    
    public static String GetAttribute(String Type){
        return ActionList.get(Type).Attribute;
    }
    
    public static Collection<String> GetTypes(){
        return ActionList.keySet();
    }

    public static void Execute(String ActionType, String ActionAttribute){
        System.out.println("ADM: Execute - ActionType = '" + ActionType + "' Action = '" + ActionAttribute + "'");
        if (!ActionType.equals(ActionTypeDefault)){
            if (!GetAttribute(ActionType).equals(Blank)){
                //Set a Static Context for the Attribute to the ActionAttribute
                System.out.println("ADM: Execute - Setting Static Context for = '" + GetAttribute(ActionType) + "' to '" + ActionAttribute + "'");
                sagex.api.Global.AddStaticContext(MyUIContext, GetAttribute(ActionType), ActionAttribute);
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
        passvalue[0] = sagex.api.WidgetAPI.FindWidgetBySymbol(MyUIContext, WidgetSymbol);
        if (passvalue[0]==null){
            System.out.println("ADM: ExecuteWidget - FindWidgetSymbol failed for WidgetSymbol = '" + WidgetSymbol + "'");
            return Boolean.FALSE;
        }else{
            System.out.println("ADM: ExecuteWidget - ExecuteWidgetChain called with WidgetSymbol = '" + WidgetSymbol + "'");

            try {
                sage.SageTV.apiUI(MyUIContext.toString(), "ExecuteWidgetChainInCurrentMenuContext", passvalue);
            } catch (InvocationTargetException ex) {
                System.out.println("ADM: ExecuteWidget: error executing widget" + util.class.getName() + ex);
                return Boolean.FALSE;
            }
            return Boolean.TRUE;
            //            sagex.api.WidgetAPI.ExecuteWidgetChain(MyUIContext, WidgetSymbol);
            
        }
               
    }

}
