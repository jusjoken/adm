# Introduction #

Check here for basic information about Another Dynamic Menu (ADM) and how to use various functions.

Also check out the [FAQ](http://code.google.com/p/sagetv-adm/wiki/ADMFAQ)

![http://dl.dropbox.com/u/7826058/ADM/ADMCustomMenu-small.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMCustomMenu.JPG) _click to view_



![http://dl.dropbox.com/u/7826058/ADM/ADMManagerOverview-small.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMManagerOverview.JPG) _click to view_

# Details #

## Changes in Version 0.42 ##

  * New Quick Launch Menu (QLM)
    * Optional new feature to display a menu on top of nearly any SageTV screen making the full ADM menu available quickly throughout SageTV
    * With Diamond you can also optionally have the diamond Widgets displayed with the QLM
  * Updated Action management
    * Used when editing or creating Menu Items for the selection of the Action to perform
    * Simpler approach (I hope) to selecting and finding the appropriate action (from a list of 120 available actions)
    * Can be filtered by Music, TV, Video, etc
    * Filter can be set to "sticky" to be available for subsequent uses when creating/adusting multiple similar menu items
  * New Dynamic List Actions
    * These menu items will be replaced with a dynamic list of menu items when they are displayed in the menus
    * Available Types
      * Music Playlist - current list of Music Playlists
      * Video Playlist - current list of Video Playlists
      * TV Revordings - list of 4-8 recording views depending on your settings
      * Diamond Custom Flows - list of currently configured Diamond Custom Flows
  * New Malore Actions
    * Malore menu actions have been added/updated to work with the CopyMode
    * New Malore Menu Actions are now available to jump directly to a specific Alternate View
  * Updated Default Menus
    * All menu items in the Default Menus are now ADM controllable menu items
    * Removed the use of any SageTV submenus from the default menus as they are less flexible
    * Note: SageTV Submenus are still supported and can be used with ADM except in the new QLM feature
    * Previous Users of ADM: if you have minor modifications to your ADM menus then I recommend loading the Default Menus with this version and then applying your minor modifications again.  This will allow you to take full advantage of showing/hiding all menu items as well as use the new QLM feature.  If you have larger modifications and your current menus are working well for you then there is no need to change as this version is fully backwards compatible.
  * ADM Manager option buttons (left side) now scrollable when required
  * Other minor fixes

## Changes in Version 0.40 ##

  * New User Based Menu Item control
    * Block the display of Menu Items based on SageTV User Profiles
  * ADM integrated with SageTV's new User Profile Permissions
    * "Change UI configuration options" permission is now required to modify any ADM menus/settings
  * Now handles Large Menu Lists with any number of items
    * All 3 levels now support large menu item lists
    * scrollbars automatically added when required
  * ADM Manager
    * Menu Items can now be reordered in either of the List by modes (Structured/Grouped)
    * Options item updated to allow configuring the Max items to be displayed on each level before scrolling starts
  * Other minor fixes

## Changes in Version 0.38 ##

  * New Action Type to Launch an External Application
  * Fixed copy of Menu Items that used a Menu Widget (like the Recipe viewer)

## Changes in Version 0.37 ##

  * New Action Type to Launch Specific File Browser Views
    * actions available for Local, Network, Server, Imports and Recording Directories
  * Updated ADM Copy
    * now recognizes File Browser Views
    * will allow copy of custom Plugin Menu items that launch a Dialog Menu such as the "Restart Sage from Setup Menu" Plugin
  * Other minor fixes

## Changes in Version 0.36 ##

  * Updated ADM Copy
    * now recognizes Sage TV Recordings Views (4 standard plus up to 4 custom)
    * handles Combined and Separate Video Folder views
  * New Action Type to Launch Specific TV Recordings View
    * works for Standard SageTV as well as Diamond TV Views
  * New Action Type to Launch Default and Custom Diamond Flows (if using Diamond)
  * New ADM Background Manager
    * graphically select a Background for any Menu Item on any Level
    * add your own Custom Backgrounds and associate them to any Menu Item
  * Update for Video Folder Browser
    * improved support for Linux systems
    * handles Combined and Separate Video Folder views
  * Fix for Clients/Extenders having their own Menu Settings
  * Major refactoring of base code
  * Other minor fixes

## Changes in Version 0.35 ##

  * New ADM Copy Feature
    * allows for the copy of Sage Menu items (Standard and from other Plugins) to ADM Menu Items
    * allows for the creation of ADM Menu Items directly from the Videos Folder Browser for an specific Folder
    * see [#ADM\_Copy\_Mode](#ADM_Copy_Mode.md) for more info
  * Fix for Linux path issues
  * Fix for issues related to selecting a different Parent
  * Other minor fixes

## Getting Started ##

  * Works with SageTV 7.1.9

  * available as a "UI Mod" plugin under SageTV version 7+ called "ADM - Another Dynamic Menu"
  * works with Standard SageTV as well as with Diamond (see Diamond section below)
  * ADM Manager is always available from the Main Menu Options menu
  * How it works
    * on initial load ADM will load either a Standard Sage menu item list or a Diamond list if Diamond is detected
    * ADM uses the Standard SageTV "Menu System" to display a dynamic list of user customizable Menu Items
    * the menu Look and Feel is not ADM's - as it uses this from SageTV
    * you can add, delete, hide any Menu Item or even start from scratch (Delete ALL) and build an entire new custom Menu
    * if you "mess up" you can always load the default menus and start again
    * the menu items are saved in the Sage .properties file but can be Exported and Imported from other clients

## ToDo List ##
  * Check this list out if you are "wondering" if a new feature is planned for ADM
    * Other user suggestions if a feature is missing

## QLM - Quick Launch Menu ##
> |![http://dl.dropbox.com/u/7826058/ADM/ADMQLMMenu-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMQLMMenu.JPG) _QLM_ |![http://dl.dropbox.com/u/7826058/ADM/ADMQLMDiamond-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMQLMDiamond.JPG) _QLM Diamond_ | _click to view_ |
|:------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------|:----------------|
### QLM Features ###
    * Optional feature to display a menu on top of nearly any SageTV screen making the full ADM menu available quickly throughout SageTV
    * To enable - see below
    * QLM is a single panel Menu that pops up in place on the left side of any SageTV screen when the Home key is pressed.  The exception is that it will not pop up on the SageTV Main Menu screen for obvious reasons.
    * All ADM Menu items are available on this menu and function as they do on the Main Menu.  The exception is that SageTV Submenus are not available.  If you are missing a menu item on QLM then go to ADM Manager and change the item to contain each of the appropriate ADM Menu Items rather than a SageTV Submenu.
    * The QLM footer indicates the "bread crumb" trail so you know which menu the current items belong to.
    * Use the Info button to edit any of the QLM Menu Items in place (you must have UI Configuration security in Sage to do this)
    * Use the Options button to display QLM Menu Options - see below
    * With Diamond you can also optionally have the diamond Widgets displayed with the QLM.  See [#Diamond\_Specific\_Functions](#Diamond_Specific_Functions.md)

> |![http://dl.dropbox.com/u/7826058/ADM/ADMQLMOptions-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMQLMOptions.JPG) _Options_ |![http://dl.dropbox.com/u/7826058/ADM/ADMQLMDiamondOptions-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMQLMDiamondOptions.JPG) _Menu Options_ | _click to view_ |
|:----------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
### QLM Options ###
    * Configure QLM Options from ADM Manager by selection QLM Options from the left side items
    * The following options can be configured here....
      * Use QLM - On - Off - must be On for the following options to be configured
      * Max Menu Items before Scroll - set how many items appear on the menu at one time before scrolling.
      * Menu Title - set the title that apprears in the Menu Header - can be left blank if you do not want a title to display
      * Show Diamond Widgets with QLM - only available for Diamond users - will display the Diamond Widgets on the right side of the screen when QLM is open
      * Home/Left behavior - use Left/Right or Select to move between the following options.  This affects the behavior of these buttons while QLM is open.
        * Home: Close QLM - Left: Close QLM
        * Home: Main Menu - Left: Close QLM
        * Home: Close QLM - Left: Main Menu
  * QLM Menu Options
    * Available directly from QLM when the menu is displayed
    * Use the Options button to bring up the QLM Menu Options
    * Depending on the item and the current settings you can....
      * Edit Menu Item 'xxxxxx' - brings up the Edit dialog that is available in ADM Manager.  You can edit the name or any of the Menu Item's settings. A shortcut to this is pressing the Info button.
      * Goto SageTV Main Menu - closes QLM and jumps to the SageTV Main Menu
      * Close QLM - Closes the QLM Menu
      * Close - closes the QLM Options dialog but leaves QLM open
      * ADM Manager - jump directly to the ADM Manager
      * Switch User - displays the Switch User interface. See [#Switch\_User\_(example)](#Switch_User_(example).md)

## ADM Manager ##

### Launching the ADM Manager ###

![http://dl.dropbox.com/u/7826058/ADM/ADMManagerAccess-small.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMManagerAccess.JPG) _click to view_
  * Press Options from the Main Menu
  * Select ADM Manager

### Enable - Disable ADM ###
  * If Enabled then SageTV will use the menu items that you built using ADM Manager
  * If Disabled then SageTV will use the built in menu items available in SageTV
  * note:see the [#Troubleshooting](#Troubleshooting.md) section for more info on using this feature

### List by: Menu Structure or Menus Grouped ###
> |![http://dl.dropbox.com/u/7826058/ADM/ADMListbyStructure-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMListbyStructure.JPG) _Menu Structure_ |![http://dl.dropbox.com/u/7826058/ADM/ADMListbyGrouped-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMListbyGrouped.JPG) _Menus Grouped_ | _click to view_ |
|:---------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------|:----------------|
  * Menu Structure
    * Lists all menus in the natural order they will appear in SageTV
    * Indentation is used to indicate menu depth (level changes)
  * Menus Grouped
    * Lists all menus Grouped with other menu items at the same level with the same Parent
    * The Menu Item's Path is displayed to indicate menu depth (level changes) and groupings
  * Menu item Order can be changed within the same Level
    * Press RIGHT when on a Menu Item
    * Press UP/DOWN to move the item within the Menu Level
    * Press LEFT to exit the move function
  * Default Menu Item will be indicated by an "**" after the Display Name
    * The Default item is the one that will have focus when a new menu is opened
  * Sage Submenus are indicated by their name such as '**

&lt;Submenu&gt;

' after the Menu Item Display Name

### Delete All Menu Items ###
  * Allows you to start from scratch and create a new set of Menu Items
  * A confirmation will be displayed
  * current Menus are backed up to ADMbackup.properties in the userdata/ADM folder
  * a single new Menu Item will be added for you to start your new Menus

### Export/Import ###
> |![http://dl.dropbox.com/u/7826058/ADM/ADMExport-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMExport.JPG) _Export_ |![http://dl.dropbox.com/u/7826058/ADM/ADMImport-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMImport.JPG) _Import_ | _click to view_ |
|:-------------------------------------------------------------------------------------------------------------------|:-------------------------------------------------------------------------------------------------------------------|:----------------|
  * Export
    * Creates a .properties style file with all the values required to store and retrieve your Menus
    * Export files can be Imported to any other client. If the clients share the Server as the default SageTV working folder then the Export will be available in the Import list. Otherwise you can copy/paste Export files using your OS's file system to make it available for Import.

  * Import
    * Offers a list of export .properties style files to be imported
    * an Imported file will replace the current Menus
    * current Menus are backed up to ADMbackup.properties in the userdata/ADM folder

  * Note: Export/Import files are not meant to be manually edited and may cause issues within ADM if this is done.

### Load Default Menus ###
  * Loads a dynamic list of menus items based on your current SageTV configuration (not including plugins other than Diamond)
  * A confirmation will be displayed
  * current Menus are backed up to ADMbackup.properties in the userdata/ADM folder
  * Default menus are designed to "mimic" the structure of the built in SageTV menus
  * Note: Default menus will NOT include any menu customizations from other installed plugins.  These must later be manually added or copied using the ADM Copy Mode
  * Default menus after being loaded can then by edited and saved by using the Export feature
  * Default menus as of 0.42 no longer use the SageTV Submenu function and all menu items should be able to be edited at all levels

### ADM Options ###

#### Maximum Menu Items ####
> |![http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusMaxOptions-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusMaxOptions.JPG) _Options_ | _click to view_ | |
|:------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|:|
> |![http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusLevel1-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusLevel1.JPG) _Level 1_         |![http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusLevel2-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusLevel2.JPG) _Level 2_ |![http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusLevel3-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMLargeMenusLevel3.JPG) _Level 3_ | _click to view_ |
  * Select Options (Basic) or Options (Advanced) from the left side menu in ADM Manager
  * Select any of the 3 Levels to adjust the Maximum Menu Item before the menus will scroll
  * Close the dialog when done.

#### Basic - Advanced Options ####
> |![http://dl.dropbox.com/u/7826058/ADM/ADMEditBasic-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMEditBasic.JPG) _Basic_ |![http://dl.dropbox.com/u/7826058/ADM/ADMEditAdvanced-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMEditAdvanced.JPG) _Advanced_ | _click to view_ |
|:------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------|:----------------|
  * Can be toggled from/to Advanced/Basic modes by selecting Options on the left menu in ADM Manager and then toggling the Advanced/Basic option button.
  * Advanced Options Mode
    * lets you see the Widget Symbols and internal Menu Names on lists while in ADM Manager
    * offers one extra Action that lets you type in a Widget Symbol (which it validates) rather than picking from the internal lists
    * adds a "Clear All ADM Settings" button to the Options Dialog.  See [#Clear\_All\_ADM\_Settings](#Clear_All_ADM_Settings.md)

### Clear All ADM Settings ###
  * Advanced Options must be enabled. If the left hand menu in ADM Manager shows "Options (Basic)" then select this button to open the ADM Options Dialog and then change the "Currently using Basic Options" button to "Currently using Advanced Options" which will then also make "Clear All ADM Settings" available in the ADM Options Dialog.
  * Resets all settings to pre-installed values (removes all ADM settings in the sage Client and Server .properties file)
  * current Menus are backed up to ADMbackup.properties in the userdata/ADM folder
  * removes all Custom Backgrounds
  * Loads the Default Menus. See [#Load\_Default\_Menus](#Load_Default_Menus.md)
  * Enables ADM
  * Disables Advanced Options

### Editing current Menu Items ###

  * Select any Menu Item from the right hand list and a dialog will offer options
    * Edit
      * edits the currently selected Menu Item
    * Add Menu Item below
      * Adds a new menu item at the same level as the current Menu Item. See [#Creating\_new\_Menu\_and\_Submenu\_Items](#Creating_new_Menu_and_Submenu_Items.md).
    * Add Submenu Item below
      * Adds a new menu item at the next higher level as the current Menu Item (current level + 1). See [#Creating\_new\_Menu\_and\_Submenu\_Items](#Creating_new_Menu_and_Submenu_Items.md).
      * not always available if a submenu can not be added at that level
    * Delete
      * See [#Deleting\_Menu\_Items](#Deleting_Menu_Items.md)

  * Display Name
    * This is the name that will be displayed on SageTV Menus

  * Parent
    * The current parent for this Menu Item
    * Selecting this item allows you to move the Menu Item to another Parent by selecting from a list of valid parents
    * Selecting Top Level will move the Menu Item to be a Level 1 menu item. See [#Menu\_Structure\_Terms](#Menu_Structure_Terms.md)
    * Note: if you select a Parent that is using a Sage Submenu then the Submenu setting for that Parent will be cleared.

  * Sage Submenu
    * if available and entered then this Menu Item will use a Standard SageTV Submenu instead of one created in ADM
    * Only available if there are no existing Children Menu Items associated to this Menu Item (this Menu Item as their Parent)
    * Note: if you set another Menu Item to have this Menu Item as it's Parent, then the Submenu setting will be cleared.
    * See also [#Children/Submenus](#Children/Submenus.md)

  * Default Menu Item
    * this item if set to Yes will show as the selected menu item when a menu opens in SageTV
    * only 1 menu item in a Menu Group can be the default so setting one to the default will remove the current default
    * there must be 1 and only 1 default and ADM will enfore this
    * Note: for the first Menu Item in a group if it is already the default you can not change its Default setting to No.  you must set another Menu Item to Yes instead.

> #### Action Management ####
> > |![http://dl.dropbox.com/u/7826058/ADM/ADMActionManager-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMActionManager.JPG) _Action Manager_ |![http://dl.dropbox.com/u/7826058/ADM/ADMActionManagerFiltered-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMActionManagerFiltered.JPG) _Filtered_ | _click to view_ |
|:-----------------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|

  * Selecting an Action
    * Select from a list of Actions on the right hand side - approximately 120 different actions available as of 0.42
    * The selected Action will launch when the Menu Item is selected within the SageTV menu system
    * If more information is needed from the user then another dialog may open. See [#Special\_Action\_Types](#Special_Action_Types.md)
  * Filtering the Action List
    * The left hand side contains Action Categories that can be used to filter the right hand Action List
    * The default is that all Actions are listed - Not Filtered -
    * One filter can be enabled at a time
    * Filters toggle between the following states
      * Off - by default - radio button is disabled - no filtering for this item
      * On - radio button is enabled - the list is filtered by this Action Category
      * Sticky - radio button is enabled - text is displayed in red - the list is filtered by this Action Category
      * Sticky - a sticky item will still be selected the next time the Action dialog is opened.  This is handy when creating a number of menu items for the same category of actions, such as TV or Music.


> #### Special Action Types ####

  * Execute Widget by Symbol (Advanced mode only)
    * Offers a text entry box to type a Widget Symbol to use for the Menu Item Action
    * The Symbol entered will be validated against SageTV to ensure it exists.
    * Note: not all Widget Symbols can be used as a valid action. So even if it exists does not mean it will give you the desired results.  Use with caution.
    * The entered Widget Symbol will execute when the Menu Item is selected within the SageTV menu system

  * Video Browser with specific Folder
> > |![http://dl.dropbox.com/u/7826058/ADM/ADMVideoFolders-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMVideoFolders.JPG) _Video Folders_ |![http://dl.dropbox.com/u/7826058/ADM/ADMVideoFoldersSetFolder-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMVideoFoldersSetFolder.JPG) _Set Folder_ | _click to view_ |
|:--------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|

  * Set this option and enter a valid Folder and this menu item will jump to that specific folder when selected
  * The Folder entered must be exactly as displayed in the Videos by Folder screen next to Folder:

> ![http://dl.dropbox.com/u/7826058/ADM/ADMVideoFoldersPath-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMVideoFoldersPath.JPG) _click to view_
    * The Folder name is case sensitive and should have the trailing "/" or "\"
    * Note: SageTV uses a different process for the Path between Separate and Combined Folder settings. If you have a working Video Folder menu item and change the Combined/Separate Video Folder setting the menu item may not go to the proper Folder and may need to be edited.
    * see [#ADM\_Copy\_Mode](#ADM_Copy_Mode.md) for information on creating this action type through a Copy function

  * Launch Specific TV Recordings View
    * Select from a list of SageTV built in Recordings Views
    * The selected Recordings View will launch when the Menu Item is selected within the SageTV menu system
    * Special features
      * After selecting a Recordings View you may be prompted to Rename the View or the Menu Item so they match
      * The View name is always displayed at the Top Left of the Recordings View
      * Renaming the view is at the SageTV level and is the same as using the Rename function in the Menu Options while displaying the specific Recordings View
      * If you change the Display Name for the Menu Item you may also be prompted to rename either the Menu Item or the Recordings View so they match.
      * You will have the option to leave them names differently
    * see [#ADM\_Copy\_Mode](#ADM_Copy_Mode.md) for information on creating this action type through a Copy function

  * File Browser: Local/Server/Imports/Network/Recordings
> > |![http://dl.dropbox.com/u/7826058/ADM/ADMFileBrowserMenuItem-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMFileBrowserMenuItem.JPG) _File Browser Item_ |![http://dl.dropbox.com/u/7826058/ADM/ADMFileBrowserResult-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMFileBrowserResult.JPG) _File Browser_ | _click to view_ |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------------------------------|:----------------|

  * Set this option and enter a valid Path and this menu item will jump to that specific Path when selected
  * The Path entered is free form and will not be validated, but if invalid will result in a Path Not Found error in the File Browser
  * Use the ADM Copy Mode to improve accuracy when creating this type of action
  * The following File Browser Action Types are available to support the similar File Browser
    * Local - local file system browser
    * Server - browses the file system on the SageTV Server
    * Network - specific Network browse functions
    * Imports - browser for SageTV Import Paths
    * Recordings - browser for SageTV Recording Directories
    * Note: many of these are "interchangeable" in that they act similarily and may launch the same Action Path as another type.  The main difference will be seen after launching the Menu Item and getting to the Path the different types may present different Options and Back button behavior.  Experiment with the Action Types to get your desired resutls.

  * Launch External Application
> > |![http://dl.dropbox.com/u/7826058/ADM/ADMLaunchExternalAppMenuItem-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMLaunchExternalAppMenuItem.JPG) _External App_ |![http://dl.dropbox.com/u/7826058/ADM/ADMLaunchExternalAppConfigure-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMLaunchExternalAppConfigure.JPG) _Configure_ | _click to view_ |
|:---------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|

  * Select this option and then on the Configure Dialog set each of the appropriate settings
    * Application: select an application using the dialog
    * Arguments: include any arguments (if any) to be passed to the application when it it launched
    * Window Type: Select from the valid Window Type by pressing ENTER, LEFT or RIGHT
      * Normal, Maximized, Minimized, Hidden
    * Wait For Exit: determines if Sage will Wait for the application to complete (spinning circle wait) or be available again once the application is launched
      * Wait until Application Exits
      * Do not wait
    * Sage Status: determines what Sage will do on launch
      * Put Sage in Sleep Mode
      * Exit Sage after application launch
      * Do nothing with Sage

  * Dynamic Lists
    * These menu items will be replaced with a dynamic list of menu items when they are displayed in the menus
    * The replacement is "in place". The Dynamic Item you create will not be displayed on menus but in it's place will be the specific list of items.
    * Available Types
      * Music Playlist - current list of Music Playlists
      * Video Playlist - current list of Video Playlists
      * TV Revordings - list of 4-8 recording views depending on your settings
      * Diamond Custom Flows - list of currently configured Diamond Custom Flows
    * Note: as these items are replaced with a list of items these actions can not have Child or Submenu items associated to them

  * Diamond Default Flows
    * Select from a list of Diamond built in Flows
    * The selected Default Flow will launch when the Menu Item is selected within the SageTV menu system

  * Diamond Custom Flows
    * Select from a list of Custom Diamond Flows. These flows must be created within Diamond using Custom Views Setup.
    * The selected Custom Flow will launch when the Menu Item is selected within the SageTV menu system


> #### Background Image ####
> |![http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerStandard-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerStandard.JPG) _Select Background_ |![http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerCustom-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerCustom.JPG) _Custom Background_ | _click to view_ |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
    * Valid for ALL menu levels (as of 0.36+)
    * The ADM Background Manager will be displayed for you to select a Background
    * Select from the built in list of Themed Backgrounds or Add a Custom Background to the list and select it
    * Any image can be selected from a File Dialog using the Add Background button and is then added to the available Backgrounds to select from.
    * Settings for Custom Backgrounds added are stored in the Server Properties to make them available for selection on any Client/Extender
    * Note: make sure you select a path that will be available to the Client/Extender if you plan to export and re-import any menus containing Custom Backgrounds.
    * Note: any Menu Item set to 'None' will use it's Parent's Background (or it's Parent's Parent's Background)
    * Note: as Sage Submenu items are internal Sage Menus, they can not have a custom background assigned to the individual menu items in the submenu. They will use the background of the parent item that has the Sage Submenu setting.
    * See also - [Background Manager](#ADM.md)

> #### Show or Block Menu Item ####
    * Hides or Shows the Menu Item in SageTV including User Based blocking of the item
      * Yes - item will show for any and all users
      * No - item will not show for any user
        * The item will always show in ADM Manager but will be in a "greyed" text
        * Children of this menu item will also show "greyed" out if their parent is not "Shown"
        * Use this feature while testing or if you want a single menu Export that can be used on multiple systems where you may want one or more items hidden on some of the target systems
      * User Based - the item can be hidden from specific users based on SageTV user profiles
        * The item will always show in ADM Manager but will be in a "red" text
        * Children of this menu item will also show in "red" text if their parent is not "Shown" due to User Based settings
        * See [#User\_Based\_Control/Options](#User_Based_Control/Options.md) for more information and examples

### Creating new Menu and Submenu Items ###
  * Select any Menu Item and then select either Add Menu Item below or Add Submenu Item below
  * A new Menu Item will be created and a Text Entry dialog will open for you to set the Display Name
  * The Edit dialog will then open to set all any addition fields. See [#Editing\_current\_Menu\_Items](#Editing_current_Menu_Items.md).
  * Note: if you close out of the new Menu Item without setting it's fields it will still be created and you need to Delete it if you don't want it in your Menus.

### Deleting Menu Items ###
  * Select any Menu Item and then select Delete from the dialog
  * Will Delete the current Menu Item AND all of it's Children if there are any
  * A confirmation will be displayed
  * Note: if you don't want to delete the Children Menu Items then change their Parent first before deleting the Parent

### ADM Background Manager ###
> |![http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerCustomAdd-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerCustomAdd.JPG) _Add Custom_ |![http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerCustomAssign-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMBackgroundManagerCustomAssign.JPG) _Assign Custom_ | _click to view_ |
|:---------------------------------------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
  * Launches from ADM Manager from the left side menu
  * Allows you to Add/Remove available Custom Backgrounds without having to go to a menu item directly
  * Allows you to Assign a Background to one or more Menu Items from a single screen
    * Select a Background by scrolling LEFT or RIGHT
    * Select 'Assign' from available options
    * A list of Menu Items will be offered sorted as Grouped Menus (All Level 1, then Level 2 etc)
    * Selecting any Menu Item will change that Menu Item to use the Current Background
  * Note: any Menu Item set to 'None' will use it's Parent's Background (or it's Parent's Parent's Background)
  * Remove Custom Background
    * Select this option while displaying a Custom Background to get a prompt to remove it
    * Remove ALL custom Backgrounds will also be offered through this option - a confirm dialog will be offered as well.

## User Based Control/Options ##
> ### General Setup Steps (example) ###
    * Assumes you have not setup ANY of this previously
    * Go to Detailed Setup - Permissions
    * Select "Manage User Profiles - Configure"
    * Select "Add New User Profile"
      * you must have at least 1 User Profile other than the default Administrator Profile to use these featrues of ADM
    * Type a name for the Profile (example 'Kids')
    * Unselect any permissions you don't want this profile to have access to
      * If you want to protect the ADM settings and hide menu items you should at minimum unselect the following...
        * Change UI configuration options (will block access to changing ADM settings and other SageTV UI settings)
        * Manage permissions settings (will disallow changing their own permissions)
        * Optional - Manage general setup options (will disallow access to Detailed Setup)
      * Closed dialog when done
    * Select "Add Lock Code - Add Code"
      * Enter a code and then re-enter the code to confirm
      * This creates the Code but does NOT lock the system

> ### User Based Menu Item Control (example) ###
> > |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedBlockOnline-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedBlockOnline.JPG) _User Based_ |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedBlockOnlineADMManager-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedBlockOnlineADMManager.JPG) _Blocked Items_ |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedBlockOnlineDisallow-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedBlockOnlineDisallow.JPG) _Unselect Kids_ | _click to view_ |
|:---------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
    * Press Home to start at the ADM Main Menu (make sure ADM is enabled)
    * Assumes the active user profile is either Administrator or one with similar privileges
    * Edit the "Online" Menu Item to NOT display for the "Kids" user profile
      * Press Options
      * Select ADM Manager
      * Select the "Online" Menu Item to edit from the list on the right
      * Select "Edit" from the dialog
      * On the Menu Item Settings dialog select "Show Menu Item" and change it's setting from "Yes" to "User Based"
      * Select "Blocked Users" button (this indicates that 0 of x user profile are blocked)
      * Unselect the "Kids" user profile. By default all user profiles have access to all menu items unless specifically unselected using this function.
      * Close the Dialog.
      * "Blocked Users" should now indicate 1/x users blocked
      * Close the Menu Item Settings Dialog
      * The "Online" menu item and it's children will now be displayed in "red" to indicate it is set to User Based
      * Press Home to return to the Main Menu
    * Verify the "Online" menu is still displayed (as you are using the "Administrator" user profile)
    * Switch user to the "Kids" user profile. See [#Switch\_User\_(example)](#Switch_User_(example).md)
    * Verify the "Online" menu is NOT displayed


> ### Switch User (example) ###
> > |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserHasUIMod-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserHasUIMod.JPG) _Options_ |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserHasUIModOptions-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserHasUIModOptions.JPG) _Switch User_ |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserListUserProfiles-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserListUserProfiles.JPG) _Select Kids_ | _click to view_ |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
    * Switch to "Kids" profile
      * Press Home to start at the ADM Main Menu (make sure ADM is enabled)
      * Assumes the active user profile is either Administrator or one with similar privileges
      * Press Options
      * Select Switch User
      * Select "Set Active Permission Profile"
      * Select "Kids" from the user profile list (or another profile with similar blocked access)
        * If you disallowed "Manage general setup options" then SageTV will close the dialog and return to the Main Menu.
        * Otherwise just press Options and you will be returned to the Main Menu
      * You now have the "Kids" profile active and any Menu Item that is set to User Based with "Kids" as NOT Allowed will no longer be displayed for this user profile.
> > |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserNoUIMod-Kids-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserNoUIMod-Kids.JPG) _Switch User_ |![http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserNoUIMod-Kids-Locked-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMUserBasedSwitchUserNoUIMod-Kids-Locked.JPG) _Enter Code_ | _click to view_                                                                                                                                                                   |
    * Switch back to "Administrator" or similar profile
      * Press Home to start at the ADM Main Menu
      * Press Options
      * Switch User is automatically displayed as ADM Manager and other UI Configuration Options are not allowed for this user profile.
      * Select "Set Active Permission Profile"
      * Enter the Permission Lock Code - if the user does not know this code then they can NOT switch users
      * Select the "Administrator" user profile or one with similar privileges.

> ### Other Notes ###
    * Relock
      * If you setup the "Kids" profile as described above, you need to add a lock code but you do not need to use the "Relock" function unless you want other users (other than "Kids") to also have to enter the Lock code to manage profiles or other permission settings.


## ADM Copy Mode ##
### Copy Sage Menu Items ###
> |![http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeADMManager-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeADMManager.JPG) _Copy Mode_ |![http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeChangeSettings-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeChangeSettings.JPG) _Change Settings_ | _click to view_ |
|:----------------------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
  * allows for the copy of Sage Menu items (Standard and from other Plugins) to ADM Menu Items
    * From ADM Manager select Copy Mode is Disabled from the left hand menu
    * select Main Menu from the dialog which will Enable Copy Mode and jump to the Main Menu
    * the Internal Sage Menu will be displayed (as if ADM was Disabled)
    * move the focus to any menu item you want to copy
    * press Options/ESC
    * select Copy xxxxx Menu Item
    * Select a Parent Menu to attach this new Menu Item to
    * Edit any of the fields such as the Display Name. See [#Editing\_current\_Menu\_Items](#Editing_current_Menu_Items.md) for more info
    * Close the Edit Dialog and the menu item has been created.
    * Continue repeating these steps for any other Menu Items you want to copy
    * To finish, press Options/ESC and select Disable ADM Copy Mode
    * The Menus displayed will now be your ADM customized menus including the new ones you created
    * Return to ADM Manager if you wish to move any of the newly created Menu Items
  * supports copy of TV Recording Menu Items
    * now recognizes Sage TV Recordings Views (4 standard plus up to 4 custom)

### Copy Video Folder Browser Items ###
> |![http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFolders-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFolders.JPG) _Folder Item_ |![http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFoldersCreateItem-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFoldersCreateItem.JPG) _Change Settings_ | _click to view_ |
|:------------------------------------------------------------------------------------------------------------------------------------------|:------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
  * allows for the creation of ADM Menu Items directly from the Videos Folder Browser for a specific Folder
    * From ADM Manager select Copy Mode is Disabled from the left hand menu
    * select Video Folder Browser from the dialog which will Enable Copy Mode and jump to the Video Folder Browser
    * move the focus to any folder item you want to create a menu item for
    * press Options/ESC
    * select Create Menu Item for xxxxx
    * Select a Parent Menu to attach this new Menu Item to
    * Edit any of the fields such as the Display Name. See [#Editing\_current\_Menu\_Items](#Editing_current_Menu_Items.md) for more info
    * Close the Edit Dialog and the menu item has been created.
    * Continue repeating these steps for any other Folder Menu Items you want to create
    * To finish, press Options/ESC and select Disable ADM Copy Mode
    * Return to ADM Manager if you wish to move any of the newly created Menu Items

### Copy File Browser Items ###
> |![http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFileFolders-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFileFolders.JPG) _File Browser Item_ |![http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFileFoldersCreateItem-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMCopyModeFileFoldersCreateItem.JPG) _Change Settings_ | _click to view_ |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|:----------------|
  * allows for the creation of ADM Menu Items directly from the File Browser for a specific File Path
    * From ADM Manager select Copy Mode is Disabled from the left hand menu
    * select File Browser from the dialog which will Enable Copy Mode and jump to the File Browser
    * move the focus to any folder item you want to create a menu item for
    * Note: selecting a File rather than a Folder allow a copy but will not be a valid Menu Item Action for the File Browser
    * press Options/ESC
    * select Create Menu Item for xxxxx
    * Select a Parent Menu to attach this new Menu Item to
    * Edit any of the fields such as the Display Name. See [#Editing\_current\_Menu\_Items](#Editing_current_Menu_Items.md) for more info
    * Close the Edit Dialog and the menu item has been created.
    * Continue repeating these steps for any other File Browser Menu Items you want to create
    * To finish, press Options/ESC and select Disable ADM Copy Mode
    * Return to ADM Manager if you wish to move any of the newly created Menu Items

## Menu Structure Terms ##
> ![http://dl.dropbox.com/u/7826058/ADM/ADMMenuStructure-small.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMMenuStructure.JPG) _click to view_
> ### Top Level ###
    * Also known as Level 0
    * Holds no menu items but all Level 1 menus have this as their parent

> ### Level 1 ###
    * Also known as Top Level menu content area in Studio
    * Holds the Left Side Menu Options
    * Standard examples - TV, Videos, Music, Photos, Search etc
    * Parent is Top Level
    * Can have Children/Submenus
      * Can create new Menu Items with this Level 1 item as it's parent (Children will be Level 2)
      * or
      * Can set a Standard Sage Level 2 Submenu to be displayed

> ### Level 2 ###
    * Also known as Secondary Level menu content area in Studio
    * Holds the Middle Menu Options
    * Parents are Level 1 Items
    * Standard examples - Search Level 1 has the following Level 2 menu items - TV Airings, Videos, Music etc
    * Can have Children/Submenus
      * Can create new Menu Items with this Level 2 item as it's parent (Children will be Level 3)
      * or
      * Can set a Standard Sage Level 3 Submenu to be displayed

> ### Level 3 ###
    * Also known as Tertiary Level menu content area in Studio
    * Holds the Right Side Menu Options
    * Parents are Level 2 Items
    * Standard examples - TV Airings Level 2 has the following Level 3 menu items - Search Titles, Search People etc
    * Cannot have Children/Submenus

> ### Children/Submenus ###
    * Children and Submenus are mutually exclusive. A Menu Item either has a Submenu set for it OR it has Children associated to it.
    * Children
      * assigned by creating Submenu items on a Parent Menu Item
      * changing a Menu Item's Parent will make the current Menu Item a Child of that Parent. (it will also remove any Submenu settings for the Parent)
    * Submenus
      * assigned by setting a Menu Item's Submenu setting in the Edit dialog
      * if a Menu Item has Children assigned then the Submenu setting is not offered.  You must delete the Children first.

## Plugin Compatibilities ##
### General ###
  * any Plugin that modifies the Main Menus or their submenus may have issues and should be tested
  * I can add notes here for any reported issues, workarounds or succeses
  * If you need temporary access to a Menu that is missing then you can go to the ADM Manager and Disable ADM and the function should operate normally with the standard SageTV Menus

### Diamond ###
  * see [#Diamond\_Notes](#Diamond_Notes.md)

### MyTV ###
  * Should work with ADM disabled
  * Tested with Diamond MyTV
  * ADM Enabled
    * Add a custom Submenu (2nd level menu) and specify the MyTV submenu as it's Sage Submenu
    * The Auto Open function will not work yet (perhaps in a future release)

### Other Plugins ###
  * Works with the following Plugins Installed...
    * CVF
    * Diamond (of course)
    * Restart Sage from Setup Menu (try the ADM Copy Mode for this one)
    * Diamond MyTV
    * Caption and Subtitle Mods
    * Comskip Playback
    * EPG Back-On-Guide
    * No Default Action in Main Menu
    * Save EPG channel selection on Exit
    * BMT
    * GKusnick's Studio Tools
    * Jetty Web Server
    * Name Timed Recording General
    * SJQ
    * SageTV Web Interface
    * SJQ UI

## Diamond Notes ##
  * This plugin has been designed to work combined with Diamond if it is installed
  * Compatible with Diamond version 3.30

### Diamond Specific Functions ###
> |![http://dl.dropbox.com/u/7826058/ADM/ADMDiamondMovies-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMDiamondMovies.JPG) _Movies_ |![http://dl.dropbox.com/u/7826058/ADM/ADMDiamondVideos-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMDiamondVideos.JPG) _Videos_ | _click to view_ |
|:---------------------------------------------------------------------------------------------------------------------------------|:---------------------------------------------------------------------------------------------------------------------------------|:----------------|
  * Default Menu
    * A Diamond Specific menu will load on initial run (newly installed) and when Load Default Menus is selected
    * This menu will be dynamically created to reflect your current setting of Replace videos with Diamond Movies
      * On (Movies)
        * Uses a Dynamic List item to display the list of Diamond Custom flows at the top of the menu
        * After the Custom Flows all the standard video menu items will be added
        * Note: the custom flows list will change dynamically when you use the Diamond Tools to add/change Diamond Custom Flows
      * Off (Videos)
        * Each of the built in Diamond Default Flows will be displayed first
        * After the Default Flows all the standard video menu items will be added
    * ADM will detect which of these 2 menus you have enabled in Diamond and create a customized menu accordingly
> |![http://dl.dropbox.com/u/7826058/ADM/ADMDiamondDefaultMenusMovies-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMDiamondDefaultMenusMovies.JPG) _Diamond Movies_ |![http://dl.dropbox.com/u/7826058/ADM/ADMDiamondDefaultMenus-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMDiamondDefaultMenus.JPG) _Diamond Videos_ | _click to view_ |
  * Replace videos with Diamond Movies
    * This function of Diamond will not affect the visibility of the Video or Movies menu item. You must set this manually within ADM Manager by customizing the menu items
  * Hide Main Menu Items
    * This function of Diamond will not affect the visibility of the Main Menu items. You must set these manually within ADM Manager using the Show Menu Item property.
  * Background Images
    * Some Diamond themes use a single image for all Top Level menus so selecting a Background image will not affect the Diamond Menus
    * Custom Backgrounds can be added and assigned to override this Diamond behaviour
  * Diamond Movies - Custom and Default Flows
    * Action types available to launch specific Default and Custom Flows
    * Action type available to provide a Dynamic List of Diamond Custom Flows
  * TV Views
    * TV Recordings views which Diamond uses are standard SageTV Recordings View with added customizations
    * These views can be launched from any ADM Menu Item (as of ADM 0.36+)
    * ADM Copy mode can be used to create these Menu Items as well
    * A Dynamic List item is also available - TV Recordings List - which will display the 4-8 Recording Views dynamically
> |![http://dl.dropbox.com/u/7826058/ADM/ADMQLMDiamondWidgets-ppc.JPG](http://dl.dropbox.com/u/7826058/ADM/ADMQLMDiamondWidgets.JPG) _QLM with Widgets_ | _click to view_                                                                                                                  |
  * QLM with Diamond Widgets
    * The QLM Options in ADM Manager offers an option to show the Diamond Widgets panel along with the QLM Menu when it is displayed
  * Future ToDos to support Diamond
    * Check out the [#ToDo\_List](#ToDo_List.md)


## Troubleshooting ##
  * Disable ADM
    * if you need temporary access to any standard SageTV menu you may not have included in your custom menu then do this...
      * Press Options from the Main Menu
      * Select ADM Manager
      * Select the button marked "ADM is Enabled"
      * Now ADM is Disabled
      * Press HOME and the Sage Standard menus will be shown
      * Follow these steps to re-enable ADM when you are done
  * Loading Default Menus
    * go to the ADM Manager
    * select Load Default Menus
    * for more info [#Load\_Default\_Menus](#Load_Default_Menus.md)
  * Clear All ADM Settings
    * If all else fails then look here to [#Clear\_All\_ADM\_Settings](#Clear_All_ADM_Settings.md)
  * Where are the Imports/Exports
    * sageroot\userdata\ADM
    * sageroot is different depending on where it is installed. It is it's working folder for the application. Same location where the sage.properties and sageclient.properties are.

