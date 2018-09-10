#!/sbin/sh

B="/system/build.prop"

busybox mount /system

#    -For uninstall this mod
#    -First, backup current build.prop to build.prop.bak
#    -Restore the original build.prop before flashing
if [ -f /system/build.prop.bak ];
  then
    rm -rf $B
    cp $B.bak $B
  else
    cp $B $B.bak
fi

echo "" >> $B

for mod in misc;
  do
    for prop in `cat /tmp/$mod`;do
      export newprop=$(echo ${prop} | cut -d '=' -f1)
      sed -i "/${newprop}/d" /system/build.prop
      echo $prop >> /system/build.prop
    done
done

