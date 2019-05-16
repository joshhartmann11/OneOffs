"""
On busy days, Dominoes haults all orders.  This script periodically requests this information from
their website and lets you know when you can order. Can you guess I'm a University Student?
"""
import requests
import json
import time

URL = "https://order.dominos.ca/power/store-locator?type=Delivery&c=Victoria%20BC%2C%20BRITISH%20COLUMBIA%20V8P4H8&s=3900%20Shelbourne%20St."

while True:
    res = requests.get(URL)
    jason = json.loads(res.text)
    carryout = jason['Stores'][0]['ServiceIsOpen']['Carryout']
    delivery = jason['Stores'][0]['ServiceIsOpen']['Delivery']
    print("Delivery: {}, Carryout: {}".format(delivery, carryout), end="\r")
    if carryout or delivery:
        for i in range(5):
            print("\033[1;37;42m" + " "*50 + "\033[0m")
        print("\033[1;37;42m{0}Delivery: {1}, Carryout: {2}{0}\033[0m".format(" "*10, delivery, carryout))
        for i in range(5):
            print("\033[1;37;42m" + " "*50 + "\033[0m")
        break
    time.sleep(10)
