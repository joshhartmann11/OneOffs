import sys

FILE = "words.txt"
# Cards are overwritten by command line arguments
CARDS = ["g", "o", "o", "k", "u", "i", "i"]


def get_words(file):
    with open(file, "r") as f:
        k = f.readlines()
    return [l.replace("\n", "").lower() for l in k if len(l) > 2]


def find_applicable(words, cards):
    applicable = []
    for w in words:
        cur = w
        for c in cards:
            cur = cur.replace(c, "", 1)
            
        if(cur == ""):
            applicable.append(w)
    return applicable

cards = sys.argv[1:]
if(cards == []):
    cards = CARDS


def find_combinations(words, cards):
    
    if len(words) == 0:
        return ""
        
    combinations = {}
    for w in words:
        cur = w
        cardsLeft = list(cards)
        for c in cards:
            if c in cur:
                cur = cur.replace(c, "", 1)
                cardsLeft.remove(c)
        additional = find_applicable(words, cardsLeft)
        combinations[w] = find_combinations(additional, cardsLeft)
        
    return combinations
  
masterPermutation = []
def print_choices(combos, string):
    if(combos == ""):
        #print(sum(1 for i in string if i.islower()), string)
        masterPermutation.append(string)
        return
    else:
        for c in combos.keys():
            print_choices(combos[c], (string + "\t|\t" + c))

            
# Cut the letters from the word
cards = [c.lower() for c in cards]
words = get_words(FILE)
applicable = find_applicable(words, cards)
for i in applicable:
    print(i)
print("**************************************************")
combos = find_combinations(applicable, cards)
print_choices(combos, "")
masterPermutation.sort()
unique = []
for f in masterPermutation:
    putIn = True
    for u in unique:
        if len(f) == len(u) and sorted(f) == sorted(u):
            putIn = False
    if putIn:
        unique.append(f)
        
for u in unique:
    print(str(sum(1 for c in u if c.islower())), u)

print("LENGTH: ", len(cards))






