#!/bin/sh
if [ $1 -eq '1' ];then
	{ 
	 echo 'Current weather in ' $2 ' :' ;
	 curl -s -m 10 http://wttr.in/$2 | head -n 3 | tail -1 | sed  's/\x1B[0-9\[;]\+m//g' | awk '{ print $3 }' ;
	 echo "with a temperature of aproximative :";
	 curl -s -m 10 http://wttr.in/$2 | head -n 4 | tail -1 | sed  's/\x1B[0-9\[;]\+m//g' | awk '{ print $2 $3 }'; } |  tr "\n" " \n"&& echo ""

	
fi
if [ $1 -eq '2' ];then
	date +"%d-%m-%Y %H:%m"
fi
if [ $1 -eq '3' ];then
	  IP=$(ifconfig | grep "inet addr:" | grep -v 127.0.0.1 | sed -e 's/Bcast//' | cut -d: -f2)
	  if [ -z $IP ];then
	   IP=$(ifconfig | grep "inet " | grep -v 127.0.0.1 | sed -e 's/broadcast//' | cut -d: -f2 | awk '{print $2}')
	  fi
	 
	 { echo "Connected to :"; ifconfig | awk -v pat="$IP" '$0~pat{print $1}' RS="\n\n";  echo $IP  ; } |  tr "\n" " \n"&& echo ""
fi

# weather.sh  [number ] [otion]
# weather.sh 1 city_name -- for weather
# weather.sh 2  -- for date
# weather.sh 3 for active ip
 


