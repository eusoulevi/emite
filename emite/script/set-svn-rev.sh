#!/bin/bash
REV=`svn info --xml| grep -m 1 revision | cut -d \" -f 2`
COMMITSPENDING=`svn status | grep -c ""`
echo r$REV-c$COMMITSPENDING
for i in EmiteUI.html EmiteUIDemo.html
do
for j in target/emite-0.2.1/com.calclab.emiteui.EmiteUI/ src/main/java/com/calclab/emiteui/public
do
perl -p -i -e "s/(<title>Emite UI application )(.*)(<\/title>)/\$1r$REV+c$COMMITSPENDING \(0.2.1\)\$3/gi" $j/$i
perl -p -i -e "s/(gwt_property_release\" content=\")(.*)(\")/\$1r$REV\-$COMMITSPENDING\$3/gi" $j/$i
done
done
