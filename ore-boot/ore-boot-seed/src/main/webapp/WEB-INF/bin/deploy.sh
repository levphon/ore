#!/bin/bash

#jenkins_run 函数内的代码是放在jenkins远程运行脚本里边
svn_name="huangzz"
svn_pwd="123456"	
svn_url="svn://192.168.3.233/gop/trunk/src/gop-osapi"

#执行脚本路径
deploy_dir="/data/script/gop-osapi"
	
jenkins_run(){
	if [ ! -d "$deploy_dir" ]
	then
        mkdir "$deploy_dir"
	fi
	cd $deploy_dir
	rm -f deploy.sh pom.xml
	svn export $svn_url"/src/main/webapp/WEB-INF/bin/deploy.sh" --username $svn_name --password $svn_pwd
	svn export $svn_url"/pom.xml" --username $svn_name --password $svn_pwd
	chmod +x deploy.sh pom.xml
	./deploy.sh
}

#执行函数
#jenkins_run

#这里一定要加PATH环境变量,以为Jenkins远程操作脚本读取不了环境变量
export PATH=$PATH
source /etc/profile

#####基础变量定义######
#版本号
version=1.0.0

#获取版本号
version=`awk '/<version>[^<]+<\/version>/{gsub(/<version>|<\/version>/,"",$1);print $1;exit;}' pom.xml`
suffix='.war'
version="${version%?}"

#包名称
war_name=gop-osapi-$version$suffix
echo "<!------------------版本:$war_name ------------------->"

#部署路径
deploy_path="/app/soft/gop-osapi"

#war包路径
package_path="/app/soft/apache-maven-3.0.5/Repository/com/glsx/gop/gop-osapi/$version"

#进程名称
process_name="$deploy_path/WEB-INF/lib"
process_jar="$process_name/aopalliance-1.0.jar"


#源代码基础路径
svncode="/data/svncode/report_forms/trunk/src"

#工程源代码下载存放路径
pro_code="${svncode}/gop-osapi"
echo "$pro_code"

#检查源代码是否已经checkout
if [ ! -d "$pro_code" ]
then
	#svn操作
	cd $svncode
	svn co $svn_url --username $svn_name --password $svn_pwd
	echo "检查代码:co svn $svn_url"
	sleep 2
else
	#svn操作
	cd $pro_code
	svn update
	echo "更新代码:update svn $svn_url"
	sleep 2
fi

#删除旧包
rm -rf $package_path/*

#打包
mvn clean install -U -Dmaven.test.skip=true
echo "打包成功:$package_path"
sleep 1

#删除旧的部署文件
rm -rf $deploy_path/*

#部署war
cd $package_path
cp $war_name $deploy_path
echo "拷贝文件成功!"
sleep 1

#war包解压
cd $deploy_path
jar -xvf $war_name
echo "war包解压成功!"
#rm -rf $war_name
sleep 1

#执行文件授权
cd WEB-INF/bin
chmod +x *
echo "执行文件授权成功:$package_path"

#判断进程是否启动
ps_out=`ps -ef | grep "$process_name" | grep "$process_jar"| grep -v 'grep' | grep -v $0`
result=$(echo $ps_out | grep "$process_name")
if [[ "$result" != "" ]];then
	echo "Running"
	cd $deploy_path
	cd WEB-INF/bin
	./stop.sh
    ./start.sh dev
else
	echo "Not Running"
	cd $deploy_path
	cd WEB-INF/bin
	./start.sh dev
fi