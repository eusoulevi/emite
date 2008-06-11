#!/bin/bash

PARAMS=$#
JAR=$1
GROUP=$2
ARTIFACT=$3
VER=$4
USER=$5

# CORRECT PARAMS ###############################################################

if [ $PARAMS -lt 4 ]
then
  echo "Use: $0 <jar> <group> <artifact> <version> <username>"
  echo "$0 target/emite-0.2.8-emiteuimodule.jar com.calclab.emite emiteuimodule 0.2.8 luther"
  exit
fi

if [ $PARAMS -gt 4 ]
then
  EXTRA=$USER@
fi

DESTHOST=${EXTRA}ourproject.org
DESTREPO=/home/groups/kune/htdocs/mavenrepo 

DIRDESTREL=`echo $GROUP| sed 's/\./\//g'`

DIRDEST=$DESTREPO/$DIRDESTREL/$ARTIFACT/$VER/

ssh $DESTHOST "mkdir -p $DIRDEST"
NAME=$ARTIFACT-$VER
DESTJAR=$DIRDEST/$NAME.jar
scp $JAR $DESTHOST:$DESTJAR 
ssh $DESTHOST "md5sum $DESTJAR > $DESTJAR.md5"

POM=$DIRDEST/$NAME.pom
cat << EOF | ssh $DESTHOST "cat - > $POM"
<project>
<modelVersion>4.0.0</modelVersion>
<groupId>$GROUP</groupId>
<artifactId>$ARTIFACT</artifactId>
<version>$VER</version>
</project>
EOF

ssh $DESTHOST "md5sum $POM > $POM.md5"



