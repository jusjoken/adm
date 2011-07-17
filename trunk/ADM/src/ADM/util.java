/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author jusjoken
 */

import sagex.UIContext;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.io.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

public class util {

    public static String Version = "0.36";
    private static final UIContext MyUIContext = new UIContext(sagex.api.Global.GetUIContextName());
    public static final String PropertyComment = "---ADM MenuItem Properties - Do Not Manually Edit---";
    public static final String PropertyBackupFile = "ADMbackup.properties";
    public static final String SageADMBasePropertyLocation = "ADM/";
    public static final String SagePropertyLocation = "ADM/menuitem/";
    public static final String SageFocusPropertyLocation = "ADM/focus/";
    public static final String AdvancedModePropertyLocation = "ADM/settings/advanced_mode";
    public static final String TopMenu = "xTopMenu";
    public static final String ADMLocation = sagex.api.Utility.GetWorkingDirectory() + File.separator + "userdata" + File.separator + "ADM";
    public static final String ADMDefaultsLocation = sagex.api.Utility.GetWorkingDirectory() + File.separator + "STVs" + File.separator + "ADM" + File.separator + "defaults";
    private static final String SageBGVariablesListFile = "ADMSageBGVariables.properties";
    private static final String SageSubMenusLevel1ListFile = "ADMSageSubMenus1.properties";
    private static final String SageSubMenusLevel2ListFile = "ADMSageSubMenus2.properties";
    public static final String ListNone = "<None>";
    public static final String OptionNotFound = "Option not Found";
    public static final String ActionTypeDefault = "DoNothing";
    public static final String ButtonTextDefault = "<Not defined>";
    public static final String SortStyleDefault = "xNaturalOrder";
    public static Boolean ADMInitComplete = false;
    public static Properties SageBGVariablesProps = new Properties();
    public static Collection<String> SageBGVariablesKeys = new LinkedHashSet<String>();
    public static Collection<String> SageSubMenusKeys = new LinkedHashSet<String>();
    public static Properties SageSubMenusLevel1Props = new Properties();
    public static Collection<String> SageSubMenusLevel1Keys = new LinkedHashSet<String>();
    public static Properties SageSubMenusLevel2Props = new Properties();
    public static Collection<String> SageSubMenusLevel2Keys = new LinkedHashSet<String>();
    public static final char[] symbols = new char[36];
    

    public static void InitADM(){
        
        if (!ADMInitComplete) {

            //Init Actions
            Action.Init();
            
            //ensure the ADM file location exists
            try{
                boolean success = (new File(ADMLocation)).mkdirs();
                if (success) {
                    System.out.println("ADM: InitADM - Directories created for '" + ADMLocation + "'");
                   }

                }catch (Exception ex){//Catch exception if any
                    System.out.println("ADM: InitADM - error creating '" + ADMLocation + "'" + ex.getMessage());
                }
            
            MenuNode.LoadMenuItemsFromSage();
            
            //also load the BGVariables for BG Images on Top Level Menus
            LoadSageBGVariablesList();
            SageBGVariablesKeys.add(ListNone);
            //also load SubMenu lists for levels 1 and 2 and Diamond
            LoadSubMenuListLevel1();
            LoadSubMenuListLevel2();
            //Add in a -None- option to the list
            SageSubMenusLevel1Keys.add(ListNone);
            SageSubMenusLevel2Keys.add(ListNone);

            //clean up existing focus items
            sagex.api.Configuration.RemovePropertyAndChildren(SageFocusPropertyLocation);
        
            //generate symbols to be used for new MenuItem names
            for (int idx = 0; idx < 10; ++idx)
                symbols[idx] = (char) ('0' + idx);
            for (int idx = 10; idx < 36; ++idx)
                symbols[idx] = (char) ('a' + idx - 10);
            
            ADMInitComplete = true;

            System.out.println("ADM: InitADM - initialization complete.");

        }

    }
    
    public static void ClearAll(){

        //backup existing MenuItems before clearing settings and menus
        if (MenuNode.MenuNodeList.size()>0){
            MenuNode.ExportMenuItems(PropertyBackupFile);
        }
        
        //clear all the Sage property settings for ADM
        System.out.println("ADM: ClearAll: clear Sage Properties");
        sagex.api.Configuration.RemovePropertyAndChildren(SageADMBasePropertyLocation);
        ADMInitComplete = Boolean.FALSE;
        System.out.println("ADM: ClearAll: load default menus");
        MenuNode.LoadMenuItemDefaults();
        System.out.println("ADM: ClearAll: initialize settings");
        InitADM();
        System.out.println("ADM: ClearAll: complete - settings restored to defaults");
        
    }

    public static String GetElement(Collection<String> List, Integer element){
        System.out.println("ADM: GetElement: looking for element " + element + " in:" + List);
        Integer counter = 0;
        for (String CurElement:List){
            counter++;
            System.out.println("ADM: GetElement: checking element '" + counter + "' = '" + CurElement + "'");
            if (counter.equals(element)){
                System.out.println("ADM: GetElement: found '" + CurElement + "'");
                return CurElement;
            }
        }
        System.out.println("ADM: GetElement: not found.");
        return null;
    }
    
    //Create/Edit MenuItems Dialog Options
    public static Collection<String> GetEditOptionsList(String Name){
        Collection<String> EditOptions = new LinkedHashSet<String>();
        //Determine valid Edit Options for the passed in MenuItem Name
        EditOptions.add("admEditMenuItem"); //Edit current Menu Item
        EditOptions.add("admAddMenuItem"); //Add current Menu Item below
        if (MenuNode.GetMenuItemLevel(Name)<3){
            EditOptions.add("admAddSubMenuItem"); //Add SubMenu to current Menu Item
        }
        EditOptions.add("admDeleteMenuItem"); //Delete current Menu Item
        EditOptions.add("admCloseEdit"); //Close the Edit Menu
        System.out.println("ADM: GetEditOptionsList - Loaded list for '" + Name + "' :" + EditOptions);
        return EditOptions;
    }
    
    public static String GetEditOptionButtonText(String Option){
        String ButtonText = OptionNotFound;
        if("admEditMenuItem".equals(Option)){
            ButtonText = "Edit";
        }else if("admAddMenuItem".equals(Option)){
            ButtonText = "Add Menu Item below";
        }else if("admAddSubMenuItem".equals(Option)){
            ButtonText = "Add Submenu Item below";
        }else if("admDeleteMenuItem".equals(Option)){
            ButtonText = "Delete";
        }else if("admCloseEdit".equals(Option)){
            ButtonText = "Close";
        }
        System.out.println("ADM: GetEditOptionButtonText returned '" + ButtonText + "' for '" + Option + "'");
        return ButtonText;
    }
    
    public static void LoadSubMenuListLevel1(){
        String SubMenuPropsPath = ADMDefaultsLocation + File.separator + SageSubMenusLevel1ListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(SubMenuPropsPath);
            try {
                SageSubMenusLevel1Props.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: LoadSubMenuListLevel1: IO exception loading standard actions " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: LoadSubMenuListLevel1: file not found loading standard actions " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> SageSubMenusList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String SageSubMenusItem : SageSubMenusLevel1Props.stringPropertyNames()){
            SageSubMenusList.put(SageSubMenusLevel1Props.getProperty(SageSubMenusItem),SageSubMenusItem);
        }

        //build a list of keys in the order of the values
        for (String SageSubMenusValue : SageSubMenusList.keySet()){
            SageSubMenusLevel1Keys.add(SageSubMenusList.get(SageSubMenusValue));
            SageSubMenusKeys.add(SageSubMenusList.get(SageSubMenusValue));
        }
        
        //Add in a -None- option to the list
        SageSubMenusLevel1Props.put(ListNone,ListNone);
        
        System.out.println("ADM: LoadSubMenuListLevel1: completed for '" + SubMenuPropsPath + "'");
        return;
    }

    public static void LoadSubMenuListLevel2(){
        String SubMenuPropsPath = ADMDefaultsLocation + File.separator + SageSubMenusLevel2ListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(SubMenuPropsPath);
            try {
                SageSubMenusLevel2Props.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: LoadSubMenuListLevel2: IO exception loading standard actions " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: LoadSubMenuListLevel2: file not found loading standard actions " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> SageSubMenusList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String SageSubMenusItem : SageSubMenusLevel2Props.stringPropertyNames()){
            SageSubMenusList.put(SageSubMenusLevel2Props.getProperty(SageSubMenusItem),SageSubMenusItem);
        }

        //build a list of keys in the order of the values
        for (String SageSubMenusValue : SageSubMenusList.keySet()){
            SageSubMenusLevel2Keys.add(SageSubMenusList.get(SageSubMenusValue));
            SageSubMenusKeys.add(SageSubMenusList.get(SageSubMenusValue));
        }
        
        //Add in a -None- option to the list
        SageSubMenusLevel2Props.put(ListNone,ListNone);
        
        System.out.println("ADM: LoadSubMenuListLevel2: completed for '" + SubMenuPropsPath + "'");
        return;
    }

    public static void LoadSageBGVariablesList(){
        String StandardActionPropsPath = ADMDefaultsLocation + File.separator + SageBGVariablesListFile;
        
        //read the properties from the properties file
        try {
            FileInputStream in = new FileInputStream(StandardActionPropsPath);
            try {
                SageBGVariablesProps.load(in);
                in.close();
            } catch (IOException ex) {
                System.out.println("ADM: LoadSageBGVariablesList: IO exception loading SageBGVariables " + util.class.getName() + ex);
                return;
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ADM: LoadSageBGVariablesList: file not found loading SageBGVariables " + util.class.getName() + ex);
            return;
        }

        //sort the keys into value order
        SortedMap<String,String> ActionValuesList = new TreeMap<String,String>();

        //Add all the Values to a sorted list
        for (String ActionItem : SageBGVariablesProps.stringPropertyNames()){
            ActionValuesList.put(SageBGVariablesProps.getProperty(ActionItem),ActionItem);
        }

        //build a list of keys in the order of the values
        for (String ActionValue : ActionValuesList.keySet()){
            SageBGVariablesKeys.add(ActionValuesList.get(ActionValue));
        }
        
        System.out.println("ADM: LoadSageBGVariablesList: completed for '" + StandardActionPropsPath + "'");
        return;
    }

    public static String GetSageBGVariablesButtonText(String Option){
        if (Option==null || Option.equals(ListNone)){
            return ListNone;
        }
        //determine if using Advanced options
        if (IsAdvancedMode()){
            return SageBGVariablesProps.getProperty(Option, ListNone) + " (" + Option + ")";
        }else{
            return SageBGVariablesProps.getProperty(Option, ListNone);
        }
    }

    public static Collection<String> GetSageBGVariablesList(){
        return SageBGVariablesKeys;
    }

    public static Boolean IsSageSubMenu(String SubMenu){
        return !MenuNode.MenuNodeList.containsKey(SubMenu);
    }

    public static Boolean IsAdvancedMode(){
        if ("true".equals(sagex.api.Configuration.GetProperty(MyUIContext,AdvancedModePropertyLocation, "false"))){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
    
    public static String GetSubMenuListButtonText(String Option, Integer Level){
        return GetSubMenuListButtonText(Option, Level, Boolean.FALSE);
    }
    
    public static String GetSubMenuListButtonText(String Option, Integer Level, Boolean SkipAdvanced){
        System.out.println("ADM: GetSubMenuListButtonText: Option '" + Option + "' for Level = '" + Level + "'");
        if (Option==null || Option.equals(ListNone)){
            return ListNone;
        }
        String RetVal = "";
        if (Level==1){
            RetVal = SageSubMenusLevel1Props.getProperty(Option, ListNone);
        }else{
            RetVal = SageSubMenusLevel2Props.getProperty(Option, ListNone);
        }

        //determine if using Advanced options
        if (IsAdvancedMode() && !SkipAdvanced){
            return RetVal + " (" + Option + ")";
        }else{
            return RetVal;
        }
    }

    public static Collection<String> GetSubMenuList(Integer Level){
        if (Level==1){
            return SageSubMenusLevel1Keys;
        }else{
            return SageSubMenusLevel2Keys;
        }
    }
            
    public static String GetVersion() {
        return Version;
    }
    
    public static String GetADMLocation() {
        return ADMLocation;
    }
    
    public static String GetADMDefaultsLocation(){
        return ADMDefaultsLocation;
    }

    public static UIContext GetMyUIContext(){
        return MyUIContext;
    }

    public static Float[] GetMenuInsets(){
        Float[] Insets = new Float[]{0f,0f,0f,0f};
        Float[] DiamondInsets = new Float[]{0f,0f,0.015f,0f};
        if (Diamond.IsDiamond()){
            System.out.println("ADM: GetMenuInsets: Diamond");
            return DiamondInsets;
        }else{
            System.out.println("ADM: GetMenuInsets:");
            return Insets;
        }
    }

    public static String EvaluateAttribute(String Attribute){
        System.out.println("ADM: EvaluateAttribute: Attribute = '" + Attribute + "'");
        Object[] passvalue = new Object[1];
        passvalue[0] = sagex.api.WidgetAPI.EvaluateExpression(MyUIContext, Attribute);
        if (passvalue[0]==null){
            System.out.println("ADM: EvaluateAttribute for Attribute = '" + Attribute + "' not evaluated.");
            return OptionNotFound;
        }else{
            System.out.println("ADM: EvaluateAttribute for Attribute = '" + Attribute + "' = '" + passvalue[0].toString() + "'");
            return passvalue[0].toString();
        }
        
    }

    //Save the current item that is focused for later retrieval
    public static void SetLastFocusForSubMenu(String SubMenu, String FocusItem){
        System.out.println("ADM: SetLastFocusForSubMenu: SubMenu '" + SubMenu + "' to '" + FocusItem + "'");
        sagex.api.Configuration.SetProperty(MyUIContext,SageFocusPropertyLocation + SubMenu, FocusItem);
    }

    public static String GetLastFocusForSubMenu(String SubMenu){
        String LastFocus = sagex.api.Configuration.GetProperty(MyUIContext,SageFocusPropertyLocation + SubMenu,OptionNotFound);
        if (LastFocus.equals(OptionNotFound)){
            //return the DefaultMenuItem for this SubMenu
            System.out.println("ADM: GetLastFocusForSubMenu: SubMenu '" + SubMenu + "' not found - returning DEFAULT");
            return MenuNode.GetSubMenuDefault(SubMenu);
        }else{
            //check that the focus item stored in Sage is still valid
            if (MenuNode.IsSubMenuItem(SubMenu, LastFocus)){
                System.out.println("ADM: GetLastFocusForSubMenu: SubMenu '" + SubMenu + "' returning = '" + LastFocus + "'");
                return LastFocus;
            }else{
                System.out.println("ADM: GetLastFocusForSubMenu: SubMenu '" + SubMenu + "' not valid - returning DEFAULT");
                return MenuNode.GetSubMenuDefault(SubMenu);
            }
        }
    }
}
