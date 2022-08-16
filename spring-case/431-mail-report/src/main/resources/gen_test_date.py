import datetime

def create_day_time(n):
    now = datetime.datetime.now()
    now = now - datetime.timedelta(days = n)
    return now.strftime("%Y-%m-%d %H:%S:%M")

vals = []
for i in range(0, 100):
    if (i % 32 % 6) == 0:
        # 模拟某一天没有用户的场景
        continue
    vals.append(f"('{i}_灰灰', '{i}hui@email.com', '{create_day_time(i % 32)}', '{create_day_time(i % 32)}')")

values = ',\n\t'.join(vals)
sqls = f"INSERT INTO story.u1 (name, email, create_time, update_time) VALUES \n{values};"
print(sqls)