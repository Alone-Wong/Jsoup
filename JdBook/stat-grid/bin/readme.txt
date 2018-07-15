1.先在页面上定义抓取任务和数据规则(URL无需定义)
2.数据抓取
3.第一次抓取全部数据，之后增量抓取(每次抓取前三页数据)
4.将抓取到的数据进行封装：封装的数据包括已定义的规则字段，任务id(taskid)，规则编号(ruleid = 001，多个规则编号依次递增)，数据url(相关规则数据通过(taskid-ruleid-url匹配)，
	同一批数据设置相同的inserttime(毫秒值)，id(uuid)
5.通过QueueSender类的sendMsg方法逐条发送数据到ActiveMQ