import sys, json
import requests
import ast

# URL = "https://be.zeitpages.com/board"
URL = "http://localhost:8080/addMessage"

with open("memooutput.txt", "r") as oldtxns:
    records = []
    for i in oldtxns:
        records.append(ast.literal_eval(i))

    with open("memooutput.txt", "a") as f:
        txns = json.load(sys.stdin)
        txnlist = []
        headers = {"content-type": "application/json"}
        for t in txns:
            datetime = t["datetime"]
            amount = t["amount"]
            memo = t["memo"]
            params = {"post_time": datetime, "amount": amount, "message": memo}
            txnlist.append(params)
            print("memo: "+ memo)
            print("amount: " + str(amount))


        times = [int(i["post_time"]) for i in records]

        for post in txnlist:
            if post["message"] == None or post["amount"] < 100000:
                continue
            elif post["post_time"] in times:
                continue
            else:
                r = requests.post(url = URL, data=json.dumps(post), headers=headers)
                print("post command:")
                print(json.dumps(post))
                f.write(str(post)+ "\n")

    print("Done!")
