"""
Python 3
Josh Hartmann

This script outputs a visual representation of a directory tree. This program is designed to be self contained with
the exception of sys.

To use:
python3 FolderTree.py -f [output_file] -r [root]

Dependancies:
	sys
	
Folder
	File
	File
	Folder
		File
		File
		File
	Folder
		Folder
			File
			File
Folder
	File
	File
"""

import sys
import os

class StringQueue():
	
	def __init__(self):
		self.queue = []
		
	def dequeue(self):
		dq = self.queue[0]
		self.queue = self.queue[1:]
		return dq
		
	def enqueue(self, str):
		self.queue += [str]

	def hasNext(self):
		return len(self.queue)


def printf(str, tabs):
	for i in range(tabs):
		print('    ', end='')
	print(str)


# For reals though
def dfsAndPrint(rootPath, all, files, folders, depth):
	
	try:

		path = rootPath
		cont = os.listdir(rootPath)
		depth += 1
		for f in cont:
			if f[0] != '.' or all:
				if "." in f:
					if not folders:
						printf(f, depth)
				else:
					if not files:
						printf('' + f + '', depth)
						
				dfsAndPrint((rootPath + '/' + f), all, files, folders, depth)		
	
	except(PermissionError):
		printf("Permission Denied", depth)
	except(NotADirectoryError):
		pass
	return



# Just for kicks
def bfsAndPrint(rootPath):
	# For everything in the root, queue
	path = rootPath
	depth = 0
	q = StringQueue()
	cont = os.listdir(rootPath)
	for f in cont:
		if f[0] != '.':
			q.enqueue(path + '/' + f)
	
	prev = rootPath
	# While the queue is not empty
	while q.hasNext():
		# Dequeue
		next = q.dequeue()
		if prev in next:
			depth = depth + 1
		# Print
		printf(next, depth)
		# If folder; Queue everything in that folder
		try:
			cont = os.listdir(next)
			for f in cont:
				if f[0] != '.':
					q.enqueue(next + '/' + f)
		except(NotADirectoryError):
			pass
		except(PermissionError):
			printf('permission denied', depth)
		prev = next
			
	return



	
try:
	root = "/Users"
	all = False
	folders = False
	files = False
	for arg in range(len(sys.argv)):
		if sys.argv[arg] == '-f':
			file = open(sys.argv[arg+1], 'w')
			
		elif sys.argv[arg] == '-r':
			root = sys.argv[arg+1]
		
		elif sys.argv[arg] == '-a':
			all = True
			
		elif sys.argv[arg] == '-folders':
			folders = True
			
		elif sys.argv[arg] == '-files':
			files = True
			
except(IndexError):	
	print('python3 FolderTree.py -f [output_file] -r [root]')
	quit()
	
print(root,all,files,folders)
print(dfsAndPrint(root, all, files, folders, (-1)))


