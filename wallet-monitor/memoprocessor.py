import sys, json
import requests
import ast

# URL = "http://localhost:8080/addMessage"
URL = "https://cmc-api-backend.herokuapp.com/addMessage"

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
            memo = None
            if 'memo' in t:
                memo = t["memo"]
            params = {"post_time": datetime, "amount": amount, "message": memo}
            txnlist.append(params)


        times = [int(i["post_time"]) for i in records]

        for post in txnlist:
            print(post);
            if post["message"] == None or post["amount"] < 100000:
                continue
            elif post["post_time"] in times:
                continue
            else:
                r = requests.post(url = URL, data=json.dumps(post), headers=headers)
                print("post command:")
                print(json.dumps(post))
                f.write(str(post)+ "\n")

    print("Done!!!")
