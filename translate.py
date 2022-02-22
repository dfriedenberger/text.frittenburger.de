
import requests
import urllib.parse
import argparse

parser = argparse.ArgumentParser(description='Process some integers.')
parser.add_argument('--language', help='language')
parser.add_argument('--text', help='text')



def translate(language,text):
    url = 'https://text.frittenburger.de/parse'
    payload = 'language=' + language+'&text=' + urllib.parse.quote(text)
    print(payload)

    headers = {
        'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
        'Authorization' : 'Bearer limited-guest-access'}
    r = requests.post(url, data=payload, headers=headers,verify=False)
    print(r.status_code,r.reason)
    result = r.json()
    for sentence in result['text']['sentences']:
        print("text", sentence['text'])
        print("tokens",sentence['tokens'])



args  = parser.parse_args()

translate(args.language,args.text)