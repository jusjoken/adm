/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author jusjoken
 */
public class Action {

    private String Type = "";
    private String ButtonText = "";
    private String Attribute = "";
    private String WidgetSymbol = "";
    private String FieldTitle = "";
    private static Map<String,Action> ActionList = new LinkedHashMap<String,Action>();
    private static Boolean InitComplete = Boolean.FALSE;
    public static final String WidgetbySymbol = "ExecuteWidget";
    public static final String BrowseVideoFolder = "ExecuteBrowseVideoFolder";
    public static final String StandardMenuAction = "ExecuteStandardMenuAction";
    public static final String TVRecordingView = "ExecuteTVRecordingView";
    public static final String ActionTypeDefault = "DoNothing";

    public Action(String Type, String ButtonText){
        this(Type,ButtonText,"Action","","");
    }

    public Action(String Type, String ButtonText, String FieldTitle){
        this(Type,ButtonText,FieldTitle,"","");
    }

    public Action(String Type, String ButtonText, String FieldTitle, String WidgetSymbol){
        this(Type,ButtonText,FieldTitle,WidgetSymbol,"");
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
            ActionList.put(BrowseVideoFolder, new Action(BrowseVideoFolder,"Video Browser with specific Folder","Video Browser Folder","OPUS4A-174637"));
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
    
}
