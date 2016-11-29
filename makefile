commit:
	git config credential.helper store
	git init
	git add .
	git commit -m "First Commit"
	git remote add origins https://github.com/Ghui/SendAndReceive-Email.git
	git remote -v
	git push origins master
	cp  makefile ../Picmaker
