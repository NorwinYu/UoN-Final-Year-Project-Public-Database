package com.example.codeclan.recruitment.components;

import com.example.codeclan.recruitment.models.Job;
import com.example.codeclan.recruitment.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    @Autowired
    JobRepository jobRepository;

    public DataLoader() {
    }

    public void run(ApplicationArguments args){

        Job job1 = new Job("JP Morgan", "declan06023@hotmail.com",
                "011241131", "Joesph", "Software Developer",
                "24,000", "Glasgow", "https://www.google.com",
                "We are committed to ensuring that all job applicants are treated equally," +
                " without discrimination because of gender, sexual orientation, marital or civil partner status, gender reassignment," +
                " race, colour, nationality, ethnic or national origin, religion or belief, disability or age.", "JavaScript",
                "Your team is looking to have someone who is committed and passionate but also looking to have a good time.",
                "You will be responsible for maintaining and extending the company's core flagship cloud-based product. " +
                        "You would be working within the backend team & therefore " +
                        "you'll have involvement in all aspects of design and development.",
                "2 years of JavaScript and SQL Knowledge",
                "Software Developer", "Senior", "Full-Time");
        jobRepository.save(job1);
        Job job2 = new Job("Spotify", "person123@gmail.com",
                "013241231", "Megan", "Software Engineer - CoreX Backend",
                "30,000", "London", "https://www.spotify.com",
                "Innovation, collaboration, transparency, passion, and playfulness are a start." +
                        " We believe our culture is a core part of what makes Spotify successful." +
                        " We’re looking for someone who understands a good culture" +
                        " and will help shape it as it evolves.", "Java/iOS/Android",
                "You will join a team where everyone shares a common interest in building" +
                        " apps on different platforms and work on distributed backend systems," +
                        " their scalability, and continued development. Together we try to find new" +
                        " ways to create a great Spotify experience for all our users at all times." +
                        " Above all, your work will impact the way the world experiences music.",
                "As a mobile & backend engineer you will be instrumental in building the next " +
                        "generation mobile interface. This is a chance to work on a distributed " +
                        "microservices architecture, building services for high volume traffic " +
                        "and build scalable systems.", "3+ years of Java", "Software Developer",
                "Mid-Level", "Full-Time");
        jobRepository.save(job2);
        Job job3 = new Job("Big Pixel Studios", "jeses@bigpixelstudios.co.uk",
                "01413237434", "Jeses", "QA Tester",
                "28,000", "Glasgow", "https://www.bigpixelstudios.co.uk/",
                "We are passionate about gaming and creating fun and interactive products", "Swift",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "As an embedded tester, helping to build a new QA department within an experienced London based studio, " +
                        "you’ll take ownership of significant elements of testing in our games as well as being an excellent communicator " +
                        "with strong people and technical skills. " +
                        "you'll have involvement in all aspects of design and development.",
                "Knowledge of iOS and Android Family of devices and mobile submission process and knowledge of F2P",
                "Tester", "Junior", "Full-Time");
        jobRepository.save(job3);

        Job job4 = new Job("Cudo Ventures Ltd", "hello@cudo.co.uk",
                "01413232328", "Joe", "Software Developer",
                "40,000", "Bournemouth", "https://www.cudoventures.com/",
                "The company is young, exciting and with a fast growing team. If you’ve always wanted to get stuck in from the ground up," +
                        " building a business with a real mission from initial concept to a major player, this is your chance. There are also substantial " +
                        "rewards for being early members of the team. The founders have previously started multi-million pound businesses such as C4L " +
                        "(https://www.linkedin.com/in/mathewhawkins/). If you’re highly skilled and up for the rewarding challenge" +
                        " we’d love you to join the team.", "JavaScript, ReactJS, Electron, Node.js",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "We are looking for an application developer to build out our software that will be user by millions of devices. " +
                        "The software is a mixture of electron, node.js, and C/C++. Experience of building software packages, applications or " +
                        "mobile applications will be of benefit." +
                        "you'll have involvement in all aspects of design and development.",
                "You must have at least 3 years experience with software development.",
                "Software Developer", "Mid-Level", "Full-Time");
        jobRepository.save(job4);


        Job job5 = new Job("KPV LAB", "hello@kpv.co.uk",
                "01415562245", "Joe", "JavaScript/React Developer",
                "35,000", "Edinburgh", "https://www.kpv-lab.co.uk/jobs/javascript-developer/",
                "Working with us will be a refreshing change from many places… We are a small tightly knit team passionate " +
                        "about making the best things possible without unnecessary compromise. Secure angel investment gives us the freedom to " +
                        "choose the path in realising our projects, and we work to our own schedule where the only deadlines are self imposed." +
                        " we’d love you to join the team.", "JavaScript ES6, WebGL, GLSL, CSS3-4, SVG, canvas",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "We are looking for an application developer to build out our software that will be user by millions of devices. " +
                        "The software is a mixture of electron, node.js, and C/C++. Experience of building software packages, applications or " +
                        "mobile applications will be of benefit." +
                        "you'll have involvement in all aspects of design and development.",
                "You should be a fastidious JavaScript developer with experience building complex single page web applications using best" +
                        " practises. You should have a deep interest in the JavaScript scene and web technologies in general, and constantly strive " +
                        "to learn and improve." +
                        "Having a proper understanding of user experience and an appreciation for design is also crucial, all our projects are highly " +
                        "interactive and visually immersive.",
                "Software Developer", "Mid-Level", "Full-Time");

        jobRepository.save(job5);

        Job job6 = new Job("KPV LAB", "hello@kpv.co.uk",
                "01415562245", "Joe", "JavaScript/React Developer",
                "28,000", "Edinburgh", "https://www.kpv-lab.co.uk/jobs/javascript-developer/",
                "Working with us will be a refreshing change from many places… We are a small tightly knit team passionate " +
                        "about making the best things possible without unnecessary compromise. Secure angel investment gives us the freedom to " +
                        "choose the path in realising our projects, and we work to our own schedule where the only deadlines are self imposed." +
                        " we’d love you to join the team.", "JavaScript ES6, WebGL, GLSL, CSS3-4, SVG, canvas",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "We are looking for an application developer to build out our software that will be user by millions of devices. " +
                        "The software is a mixture of electron, node.js, and C/C++. Experience of building software packages, applications or " +
                        "mobile applications will be of benefit." +
                        "you'll have involvement in all aspects of design and development.",
                "You should be a fastidious JavaScript developer with experience building complex single page web applications using best" +
                        " practises. You should have a deep interest in the JavaScript scene and web technologies in general, and constantly strive " +
                        "to learn and improve." +
                        "Having a proper understanding of user experience and an appreciation for design is also crucial, all our projects are highly " +
                        "interactive and visually immersive.",
                "Software Developer", "Junior", "Internship");
        jobRepository.save(job6);

        Job job7 = new Job("Primo Associates", "hello@primoassociates.co.uk",
                "014155624345", "Sally", "Product Owner",
                "50,000", "Glasgow", "https://www.primoassociates.com/job/product-owner-scotland-glasgow-461.aspx?utm_campaign=google_jobs_apply&utm_source=google_jobs_apply&utm_medium=organic",
                "If you are a passionate and motivated team member, with a sound understanding of the fundamentals of agile iterative development,this could be the role for you!", "JavaScript ES6, WebGL, GLSL, CSS3-4, SVG, canvas",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "",
                "You should be a fastidious JavaScript developer with experience building complex single page web applications using best" +
                        " practises. You should have a deep interest in the JavaScript scene and web technologies in general, and constantly strive " +
                        "to learn and improve." +
                        "Having a proper understanding of user experience and an appreciation for design is also crucial, all our projects are highly " +
                        "interactive and visually immersive.",
                "Product Owner", "Mid-Level", "Full-Time");
        jobRepository.save(job7);

        Job job8 = new Job("AND Digital - Halifax", "hello@halifax.co.uk",
                "01415235325", "John", "Full Stack Developer",
                "25,000", "Dundee", "https://and-digital.workable.com/j/0D990DF1A9",
                "If you are a passionate and motivated team member, with a sound understanding of the " +
                        "fundamentals of agile iterative development,this could be the role for you!", "JavaScript ES6, Java, React",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "Working with our Clients onsite, you’ll deliver compelling, commercially viable digital products across multiple platforms and channels" +
                        "You’ll be working with other Business Analysts and Developers to facilitate this digital transformation at our clients using agile methodologies" +
                        "Building innovative web applications and software solutions with the latest technology and agile development methodologies" +
                        "Working within a high performing multi-discipline team, from user experience and creative to development and testing" +
                        "Representing AND Digital as the expert in Digital and eCommerce functional and design capability, consulting clients to create and deliver outstanding products\n",
                "Degree in Computer Science, Software Engineering or related areas of studies are preferred" +
                        "Passion for all things digital, software AND web applications (showcasing your own developments outside of your studies is a huge plus)" +
                        "Related Professional Work Experience; ideally one year work placement",
                "Back-End Developer", "Junior", "Internship");
        jobRepository.save(job8);

        Job job9 = new Job("SurflineWaveTrak", "hello@SurflineWaveTrak.co.uk",
                "01415235325", "Gill", "Software Engineer - Placement",
                "32,000", "Kingsbridge", "https://surfline.applytojob.com/apply/job_" +
                "20190219110449_IMGT5YS8RGYQAQLN/Software-Engineer-Placement?source=INDE",
                "This is your chance to play a role in building the key features for our surf forecasting application, " +
                        "which reaches millions of surfers across the world. With a focus on industry best-practice, combined with a " +
                        "relaxed working environment, this is the perfect opportunity for someone who wants to build great products and " +
                        "gain crucial experience to help jump-start their career.", "JavaScript ES6, Java, React",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "Write efficient, lean, performant code, with supporting tests and documentation." +
                        "Contribute to UI discussions, implement new features, take part in QA processes." +
                        "Collaborate with other team members, through paired programming, and peer review." +
                        "Participate in SCRUM and stand-up discussions.",
                "Experience with any of the following technologies:" +
                        "JavaScript ES5 / ES6 / ES7." +
                        "Advanced Javascript Frameworks, e.g. React, Backbone, Angular." +
                        "HTML5 & associated Web APIs." +
                        "CSS preprocessors, e.g. Sass, LESS, Stylus, PostCSS." +
                        "Android SDK, Objective-C or Swift." +
                        "PHP/Python full stack." +
                        "NoSQL databases (specifically MongoDB)" +
                        "SQL databases (specifically MySQL).",
                "Software Developer", "Junior", "Internship");
        jobRepository.save(job9);

        Job job10 = new Job("3 SIDED CUBE", "hello@cube.co.uk",
                "014152353232", "Gill", "PHP Developer",
                "40,000", "Bournemouth", "https://surfline.applytojob.com/apply/job_" +
                "20190219110449_IMGT5YS8RGYQAQLN/Software-Engineer-Placement?source=INDE",
                "3 Sided Cube is a multi-award winning Digital Agency based in the UK, with a particular passion for building technology for good." +
                        "Be it life-saving, life-changing or problem-solving, we champion the genius over the generic. And are looking for new " +
                        "talent to join our team.", "JavaScript ES6, Java, React",
                "You will be working with a professional team who are passionate about technology and work on " +
                        "cutting-edge projects.",
                "As a backend developer, you will have a passion for digital and have put it to good use developing and " +
                        "maintaining robust server-side technology. You will be super keen to develop your " +
                        "experience further in a hugely creative environment, working with web applications that are literally saving lives every day!",
                "Experience writing and maintaining testable code and test-driven development." +
                        "A strong understanding of web application security." +
                        "Knowledge non-relational database & search systems e.g. MongoDB, ElasticSearch, or DynamoDB." +
                        "Experience using CI & CD in a team environment." +
                        "Knowledge of server orchestration tools such as Chef, Puppet or Ansible." +
                        "Experience using containerised applications and tools such as Docker and Docker Compose." +
                        "Experience working in a DevOps environment.",
                "Back-End Developer", "Senior", "Full-Time");
        jobRepository.save(job10);

    }


}
