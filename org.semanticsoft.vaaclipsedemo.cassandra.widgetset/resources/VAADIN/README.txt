By running the build "mvn clean verify" the widgetset will be placed here.

Attention: Add the icepush-gwt.jar to your local maven repo! 
Therefore download the icepush addon from http://vaadin.com/addon/icepush (vaadin 7 version !!!) 
and unzip the archive.

Use following command to install the jar to your local repo.
mvn install:install-file -Dfile=icepush-gwt.jar -DgroupId=org.icefaces -DartifactId=icepush.gwt -Dversion=1.0.0 -Dpackaging=jar
-Dfile is the path to your downloaded icepush-gwt.jar.
 