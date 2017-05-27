#!/bin/sh

DIR="/letv/testing"
echo $DIR

IPVALUE=`grep  ip $DIR/hosts |awk -F '=' '{print $2}'` # get ip value

#echo $IPVALUE

if [ !$1 ] !! [! $2 ]; then

echo "please input IP & version"
exit 0

fi

sed -ig "s/$IPVALUE/$1/" $DIR/hosts

AES_VERSION=`grep aes_version $DIR/hosts |awk -F '=' '{print $2}'` #get the ase_version value

#echo $AES_VERSION

sed -ig "s/$AES_VERSION/$2/" $DIR/hosts

cat $DIR/hosts
pwd
cd $DIR
#./update_aes_jenkins.sh 2 1
./update_aes_jenkins.sh 2 2
#./update_aes_jenkins.sh 2 3