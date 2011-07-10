/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ADM;

/**
 *
 * @author jusjoken
 */
public class Diamond {
    
    //class functions copied from diamond 3.30 release to support diamond Menu Customization

    public static final String PropName="JOrton/CustomViews";

    public static Object GetCustomViews(){
        String views=sagex.api.Configuration.GetProperty(PropName,"");
        if(views.contains(";")){	
            System.out.println("ADM Diamond : GetCustomViews = '" + views.split(";") + "'");
            return views.split(";");
        }
        System.out.println("ADM Diamond : GetCustomViews = '" + views + "'");
        return views;
    }

    public static String GetViewName(String name){
        String[] SplitString = name.split("&&");
        if (SplitString.length == 2) {
            System.out.println("ADM Diamond : GetViewName("+name+") = '" + SplitString[0] + "'");
            return SplitString[0];
        } else {
            System.out.println("ADM Diamond ERROR: GetViewName("+name+")");
            return "Error";
        }
    }

    
}
