# Motds - A (very over-engineered) bukkit plugin to display MOTDs
Like the description says, Motds is a way-too-complicated plugin for display MOTDs to players.  It supports many nice features, however, like multiple motds, permission-filtered motds, and multiline messages.

The command /motd can be used to replay MOTDs displayed at login, if the user has permission "motd.command".

MOTDs are added with the following process:

1.Create a new file in /plugins/Motd/motds/.   
2.Name the file as something ending in ".motd".  The name does not matter, so name it whatever you want.   
3.Structure the contents as follows:   
```
    //MOTD for all players     //any comments                       (can be anywhere)
    name:: global              //name of MOTD, optional             (can be anywhere)
    perm:: motds.global        //permission node required, optional (can be anywhere)
    Welcome to AcomputerdogMC! //actual contents                    (everything not a comment, name, or permission)
```
4.Any player with the permission "motds.global" will see the message "Welcome to AcomputerdogMC!".   

If a name is not specified, the name will be the filename without ".motd".  If a permission is not specified, the permission will be "motds.<name>".

Didn't I say it was overengineered? ;)
