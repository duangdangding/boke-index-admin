/* 鼠标特效，我觉得太花哨了就注释了，喜欢的自己打开注释就可以 */
var a_idx = 0;
jQuery(document).ready(function($) {
    $("body").click(function(e) {
        var a = new Array("❤去活出你自己。❤","❤今天的好计划胜过明天的完美计划。❤","❤不要轻言放弃，否则对不起自己。❤","❤紧要关头不放弃，绝望就会变成希望。❤","❤如果不能改变结果，那就完善过程。❤",
            "❤好好活就是干有意义的事，有意义的事就是好好活！❤","❤你真正是谁并不重要，重要的是你的所做所为。❤","❤你不想为你的信仰冒一下险吗?难道想等你老了，再后悔莫及吗?❤","❤有些鸟儿是关不住的，它的每一根羽毛都闪耀着自由的光辉。❤",
            "❤决定我们成为什么样人的，不是我们的能力，而是我们的选择。❤","❤掉在水里你不会淹死，呆在水里你才会淹死，你只有游，不停的往前游。❤","❤有些路，只能一个人走。❤","❤希望你眼眸有星辰，心中有山海。❤","❤从此以梦为马，不负韶华。❤",
            "❤人的成就和差异决定于其业余时间。❤","❤佛不要你皈依，佛要你欢喜。❤","❤ダーリンのこと　大好きだよ❤","❤小猫在午睡时，地球在转。❤","❤我，混世大魔王，申请做你的小熊软糖。❤","❤决定好啦，要暗暗努力。❤",
            "❤呐，做人呢最紧要开心。❤","❤好想邀请你一起去云朵上打呼噜呀。❤","❤永远年轻，永远热泪盈眶。❤","❤我生来平庸，也生来骄傲。❤","❤我走得很慢，但我从不后退。❤","❤人间不正经生活手册。❤","❤我是可爱的小姑娘，你是可爱。❤",
            "❤数学里，有个温柔霸道的词，有且仅有。❤","❤吧唧一口，吃掉难过。❤","❤你头发乱了哦。❤","❤健康可爱，没有眼袋。❤","❤日月星辰之外，你是第四种难得。❤","❤你是否成为了了不起的成年人？❤","❤大家都是第一次做人。❤",
            "❤何事喧哗？！❤","❤人间有味是清欢。❤","❤你笑起来真像好天气。❤","❤风填词半句，雪斟酒一壶。❤","❤除了自渡，他人爱莫能助。❤","❤昨日种种，皆成今我。❤","❤一梦入混沌 明月撞星辰❤","❤保持独立 适当拥有❤",
            "❤谢谢你出现 这一生我很喜欢❤","❤做自己就好了 我会喜欢你的❤","❤太严肃的话，是没办法在人间寻欢作乐的❤","❤愿你余生可随遇而安，步步慢。❤","❤黄瓜在于拍，人生在于嗨❤","❤奇变偶不变，符号看象限。❤","❤从来如此，便对么？❤",
            "❤今天我这儿的太阳，正好适合晒钙 你呢❤","❤未来可期，万事胜意。❤","❤星光不问赶路人 时光不负有心人❤","❤我当然不会试图摘月，我要月亮奔我而来❤",
            "❤女生要修炼成的五样东西： 扬在脸上的自信，长在心底的善良， 融进血里的骨气，刻进命里的坚强，深到骨子里的教养❤","❤燕去燕归，沧海桑田。纵此生不见，平安惟愿❤","❤我想认识你 趁风不注意❤","❤我一直想从你的窗子里看月亮❤",
            "❤长大应该是变温柔，对全世界都温柔。❤","❤别在深夜做任何决定❤","❤山中何事，松花酿酒，春水煎茶。❤","❤桃李春风一杯酒，江湖夜雨十年灯。❤","❤欲买桂花同载酒，终不似，少年游。❤");
        var le = Math.ceil(Math.random()*a.length);
        var $i = $("<span></span>").text(a[le]);/*a[a_idx]*/
        a_idx = (a_idx + 1) % a.length;
        var x = e.pageX,
            y = e.pageY;
        $i.css({
            "z-index": 999999999999999999999999999999999999999999999999999999999999999999999,
            "top": y - 20,
            "left": x,
            "position": "absolute",
            "font-weight": "bold",
            "color": "rgb("+~~(255*Math.random())+","+~~(255*Math.random())+","+~~(255*Math.random())+")"
        });
        $("body").append($i);
        $i.animate({
                "top": y - 180,
                "opacity": 0
            },
            2000,
            function() {
                $i.remove();
            });
    });
});