import os
import time


while(True):

	# Run the test
	os.system("curl -s https://raw.githubusercontent.com/sivel/speedtest-cli/master/speedtest.py | python > output.txt")
	
	# Wait for 5 minutes
	t = time.clock()
	print(t)
	while((t + 60) > time.clock()):
			pass
			
	# Read the file and output to a csv
	file = open('output.txt','r')
	d = 0
	u = 0
	for line in file:
		if 'Download' in line:
			d = float(line.split(' ')[1])
		
		if 'Upload' in line:
			u = float(line.split(' ')[1])
	
	file.close()
	out = open('output.csv','a')
	out.write(str(u) + ',' + str(d) + '\n')
	out.close()
