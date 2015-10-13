# FAQ #

Look here first to see if your question has already been answered.


## Q. Where do I find details on how to use ADM? ##
### A. Check out the ADM Wiki... ###
  * The ADM Wiki can be found here [ADM Information](http://code.google.com/p/sagetv-adm/wiki/ADMInformation)

## Q. Can I add a custom background to Submenu items like the Diamond Flows in the Movies menu? ##
### A. Yes, but there are some changes you may need to make.... ###

  * If you are wanting to have the background change when a specific Custom or Default Diamond Flow Menu Item is in focus (or any other menu item) you just need to change that menu item's Background setting.
  * The trick here is some of the Default Menus are NOT displaying ADM Menu Items but instead are just displaying the built in Submenu (from Sage or Diamond or another Plugin added Submenu).  In this case you don't have control over the Background for each menu item on that Submenu.
  * However, you can re-create that Submenu using ADM Menu Items instead.  For the parent item (Videos or Movies in this case), set the Submenu to None. Then add Submenu Items to this Parent either manually or through the ADM Copy Mode feature for each of the Diamond flows etc that you want on the menu (note: even with Copy mode part of the process is manually as so far it won't recognize the specific flow type).
  * At this point you will now have a Childmenu that you have created with ADM Menu Items and each can have a different Background if you want this.

