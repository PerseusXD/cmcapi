set -x
while true
do
    ./zecwallet-cli list | py memoprocessor.py
    sleep 20
done
