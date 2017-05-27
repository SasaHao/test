#!/usr/bin/python
#coding=utf-8
import email,sys
import mimetypes
from email.MIMEMultipart import MIMEMultipart
from email.MIMEText import MIMEText
from email.MIMEImage import MIMEImage
import smtplib
import os
import random
class sendEmail:
    #
    def sendEmail(self , authInfo, fromAdd, toAdd, subject , htmlText = None , files = None):

            #subject = u'【方舟监控】日常上报错误报表（20151101）'
            #htmlText = u'20151101,<p>ac上报错误数是171200057，比前一天少-4%</p><p>ac上报禁播数是60368124，比前一天少-2%</p><p>调度上报错误数是2601826，比前一天多11%</p><p>素材播放失败数是19207160，比前一天少-3%</p><p>监测发送失败数是193811634，比前一天多6%</p><img src="cid:00000001"/><em>详见附件,或访问<a href="http://10.150.160.136:3000">http://10.150.160.136:3000</a></em>'
            #files = u'/private/var/www/amp/plus/report_20151101.html,/private/var/www/amp/plus/report_20151101.png'
            strFrom = fromAdd
            strTo = toAdd

            server = authInfo.get('server')
            user = authInfo.get('user')
            passwd = authInfo.get('password')

            if not (server and user and passwd) :
                    print 'incomplete login info, exit now'
                    return


            msgRoot = MIMEMultipart('related')
            msgRoot['Subject'] = subject
            msgRoot['From'] = strFrom
            msgRoot['To'] = ",".join(strTo)
            msgRoot.preamble = 'This is a multi-part message in MIME format.'

            # Encapsulate the plain and HTML versions of the message body in an
            # 'alternative' part, so message agents can decide which they want to display.
            msgAlternative = MIMEMultipart('alternative')
            msgRoot.attach(msgAlternative)


            #msgText = MIMEText(plainText, 'plain', 'utf-8')
            #msgAlternative.attach(msgText)


            msgText = MIMEText(htmlText, 'html', 'utf-8')
            msgAlternative.attach(msgText)



            if files != None:
                for file in files:
                    if ".png" in file:
                        att = MIMEImage(open(file, 'rb').read())
                        att.add_header('Content-ID','00000001')
#                        att["Content-Type"] = 'application/octet-stream'
#                        att["Content-Disposition"] = 'attachment; filename="' + os.path.basename(file)+ '"'
                        msgRoot.attach(att)
                    else:
                        att = MIMEText(open(file, 'rb').read(), 'base64', 'utf-8')
                        att["Content-Type"] = 'application/octet-stream'
                        att["Content-Disposition"] = 'attachment; filename="' + os.path.basename(file)+ '"'
                        msgRoot.attach(att)


            smtp = smtplib.SMTP()

            smtp.set_debuglevel(0)
            smtp.connect(server)
            smtp.login(user, passwd)
            print 'login';
            result = smtp.sendmail(strFrom, toAdd, msgRoot.as_string())
            print result;
            #smtp.sendmail()
            smtp.quit()


if __name__ == '__main__' :

        if len(sys.argv) >= 4:
            users=[['mail.letv.com','arksystem','ark0901.com','arksystem@letv.com']]
            #id=random.randint(0,len(users)-1)
            id=0
            print users[id]
            authInfo = {}
            authInfo['server'] = users[id][0]
            authInfo['user'] = users[id][1]
            authInfo['password'] = users[id][2]
            fromAdd = users[id][3]
            toAdd = sys.argv[1].split(",")
            subject = sys.argv[2]

            htmlText = sys.argv[3]
            #files = ['d:/2.txt' , 'd:/1.txt']
            files = None
            if len(sys.argv) >= 5:
            	files = sys.argv[4].split(",")
            sendEmail().sendEmail(authInfo, fromAdd, toAdd, subject , htmlText , files)

        else:
            print 'error' ,len(sys.argv)
            print 'sendMail to1,to2 title content [file1,file2]'
            for arg in sys.argv:
                print arg

