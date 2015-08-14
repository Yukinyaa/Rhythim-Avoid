import os



num_lines=0
num_words=0
num_chars=0


files = os.listdir(".")
for i in files:
	if (i[-4:]==".cpp" or i[-2:]==".h" or i[-3:]==".cs" or i[-5:]==".java"):
		print(i)
		with open(i, 'r') as f:
		    for line in f:
		        words = line.split()
		        num_lines += 1
		        num_words += len(words)
		        num_chars += len(line)




print "num_lines :",num_lines
print "num_words :",num_words
print "num_chars :",num_chars
