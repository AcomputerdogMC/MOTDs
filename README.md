# Motds - A simple bukkit plugin to display MOTDs
Like the description says, Motds is a simple plugin for display MOTDs to players.  It supports many nice features, however, like multiple motds, permission-filtered motds, and multiline messages.

The command /motd can be used to replay MOTDs displayed at login, if the user has permission "motd.command".

MOTDs are added with the following process:

1.Create a new file in /plugins/Motd/.
2.Name the file as something ending in ".motd".  The name does not matter, so name it whatever you want.   
3.Structure the contents as follows:   
```
    $perm=motds.global        //optional permission node needed for MOTD (can be anywhere)
    Welcome to AcomputerdogMC! //actual contents                          (everything not a comment, name, or permission)
```
4.Any player with the permission "motds.global" will see the message "Welcome to AcomputerdogMC!".   

If a permission is not specified, all players will see the MOTD.
