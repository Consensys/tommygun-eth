# https://docs.web3j.io/getting_started/deploy_interact_smart_contracts/
# https://docs.epirus.io/sdk/
binFile=$1
abiFile=$2
javaOutputFiles=$3
packageName=$4
epirus solidity generate -b "$binFile" -a "$abiFile" -o "$javaOutputFiles" -p "$packageName"