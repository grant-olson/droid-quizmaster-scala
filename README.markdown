Droid Quizmaster
================

Droid Quizmaster is a quiz application for Android Phone
applications.  It is written entirely in scala.  Hopefully it will
provide a more detailed example of a scala Droid app than the basic
hello-world application.

Install Prerequisites
---------------------

1. A Java JDK.

2. A scala install.  (I'm using 2.8.0).

3. The Android SDK.

4. ProGuard (to create smaller .apks)

Install
-------

1. Clone the repository.

2. Copy the files 'scala-compiler.jar' and 'scala-library.jar' from
   $SCALA_HOME/lib to the tools/ directory.

3. Copy the files 'proguard.jar' and 'retrace.jar' from
   $PROGUARD_HOME/lib to the tools/ directory.

4. run 'ant install'.

### Note:  Run 'export ANT_OPTS=-XMX1024M' if you run out of heap space while building.

Have Fun
--------

Grant
