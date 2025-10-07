// File: app/src/main/java/com/dhirekhaf/mytype/PersonalityData.kt

package com.dhirekhaf.mytype

import androidx.annotation.DrawableRes

/**
 * Model data GABUNGAN.
 * [FINAL]: Properti 'mainImage' dihapus. Gambar utama sekarang menjadi item pertama
 * di dalam 'detailImages'.
 */
data class PersonalityDetails(
    val typeName: String,
    val description: String,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val careerPaths: List<String>,
    val relationships: String,
    val developmentTips: List<String>,

    // Semua gambar rincian, dengan gambar utama (misal: intjmain) sebagai item pertama.
    @DrawableRes val detailImages: List<Int>
)

/**
 * Database utama yang berisi semua rincian untuk 16 tipe kepribadian.
 * [FINAL]: Menggunakan deskripsi baru yang lebih indah dan puitis.
 */
val personalityDetailsMap = mapOf(
    // --- ANALIS ---
    "INTJ" to PersonalityDetails(
        typeName = "INTJ - Sang Arsitek",
        description = "Ia seorang yang merancang jalan di peta yang tak kasat mata—selalu menimbang kemungkinan sebelum melangkah. Di kepalanya, dunia adalah struktur yang bisa disusun ulang menjadi sesuatu yang lebih efisien dan bermakna. Tenangnya tampak tenang, namun di balik itu ada kerangka pemikiran yang bekerja tanpa henti, menyusun strategi dan skenario untuk masa depan.",
        strengths = listOf(
            "Berpikir strategis dengan visi jangka panjang",
            "Mandiri dan mampu bekerja tanpa banyak pengawasan",
            "Objektif dalam menilai situasi dan mengambil keputusan",
            "Cepat menangkap pola dan menyusun solusi inovatif",
            "Disiplin dalam merencanakan dan mengeksekusi ide",
            "Kecenderungan untuk memperbaiki sistem demi efisiensi"
        ),
        weaknesses = listOf(
            "Bisa tampak dingin atau terlalu kritis pada orang lain",
            "Kurang peka terhadap kebutuhan emosional sekitar",
            "Cenderung menuntut kesempurnaan sampai menyulitkan diri sendiri",
            "Sulit menerima otoritas atau aturan yang menurutnya tidak rasional",
            "Kadang terjebak dalam overthinking hingga lambat bertindak",
            "Rentan merasa asing dalam interaksi sosial yang bersifat ringan"
        ),
        careerPaths = listOf(
            "Ilmuwan atau peneliti strategis",
            "Pengembang perangkat lunak / Arsitek sistem",
            "Analis keuangan atau perencana investasi",
            "Pengacara atau konsultan kebijakan",
            "Profesor atau akademisi bidang teoritis",
            "Manajer R&D atau perancang produk teknologi"
        ),
        relationships = "Dalam hubungan, ia mencintai melalui bentuk dukungan yang konkret: memberi solusi saat masalah muncul, merencanakan masa depan bersama, dan menjaga komitmen intelektual serta praktis. Ia bukan tipe yang mengekspresikan kasih lewat kata-kata manis; ia mengekspresikannya lewat tindakan terukur dan konsistensi. Kadang ia menarik diri untuk merenung — itu bukan tanda hilangnya cinta, melainkan caranya mengisi ulang dan menyusun pemikiran. Pasangan yang ideal baginya adalah yang dapat menghargai ruang pribadi, mampu berdialog secara jujur tanpa terlalu tersinggung oleh kritik, dan yang bersedia berbagi visi atau setidaknya menghormati cara ia melihat dunia.",
        developmentTips = listOf(
            "Izinkan dirimu melonggarkan standar hingga sedikit tak sempurna—itu tak berarti kegagalan, melainkan ruang untuk belajar.",
            "Latihlah ungkapan sederhana tentang apa yang membuatmu merasa dihargai; kadang kata singkat menyentuh lebih dalam daripada argumen panjang.",
            "Jadwalkan waktu untuk hadir tanpa tujuan produktif—biarkan percakapan ringan mengalir tanpa harus dianalisis.",
            "Cobalah menanyakan: 'Apa yang dibutuhkan orang ini sekarang?' sebelum memberi solusi—seringkali kehadiran lebih dibutuhkan daripada jawaban.",
            "Berikan diri penghargaan atas pencapaian kecil; bukan hanya hasil besar yang layak dirayakan.",
            "Cari mentor emosional atau teman terpercaya untuk melatih kepekaan interpersonal secara bertahap—kesadaran kecil hari demi hari akan menambah kedalaman hubunganmu."
        ),
        detailImages = listOf(
            R.drawable.intjmain,
            R.drawable.intjdesc1,
            R.drawable.intjdesc2,
            R.drawable.intjdesc3
        )
    ),

    "INTP" to PersonalityDetails(
        typeName = "INTP - Sang Ahli Logika",
        description = "Ia seorang pemikir alami yang hidup di antara arus ide dan teori. Dunia baginya adalah teka-teki besar yang menantang untuk diurai, bukan sekadar dijalani. Ia senang merenung, menelaah, dan mencari kebenaran di balik sesuatu yang tampak sepele bagi orang lain. Pikiran logis dan imajinatifnya berjalan beriringan—ia bisa terlarut dalam satu konsep hingga lupa waktu.",
        strengths = listOf(
            "Mampu berpikir analitis dan menemukan pola tersembunyi",
            "Kreatif dalam mengembangkan teori dan konsep baru",
            "Mandiri secara intelektual dan tidak mudah terpengaruh opini publik",
            "Rasa ingin tahu yang luas dan mendalam",
            "Objektif dalam menilai bukti dan argumen",
            "Kemampuan deduksi yang tajam dalam memecahkan masalah kompleks"
        ),
        weaknesses = listOf(
            "Sering terlalu lama menganalisis hingga sulit bertindak",
            "Kurang peka terhadap nuansa emosional orang lain",
            "Mudah kehilangan minat pada hal-hal praktis atau berulang",
            "Terkadang tampak terlepas dari kenyataan sosial",
            "Cenderung terlalu kritis pada ide, termasuk miliknya sendiri",
            "Sulit mengekspresikan perasaan sehingga dianggap dingin"
        ),
        careerPaths = listOf(
            "Peneliti akademik atau ilmuwan teori",
            "Programmer, data scientist, atau arsitek perangkat lunak",
            "Akademisi, dosen, atau penulis nonfiksi",
            "Analis sistem atau konsultan teknis",
            "Filsuf atau esais konseptual",
            "Inovator teknologi atau perancang algoritma"
        ),
        relationships = "Ia membangun hubungan lewat keterikatan intelektual. Percakapan bermakna membuatnya merasa terhubung, dan ia cenderung menunjukkan perhatian lewat pemahaman ide-ide pasangannya. Ia menghargai ruang pribadi dan kebebasan berpikir; tekanan emosional atau tuntutan sosial yang berlebihan bisa membuatnya mundur. Ketika ia nyaman, kesetiaannya nyata—meski tidak selalu ekspresif, ia setia dalam dukungan ide dan hadir saat dibutuhkan secara intelektual.",
        developmentTips = listOf(
            "Praktikkan berbicara sederhana tentang perasaanmu, bukan hanya ide-ide besar.",
            "Cobalah menyelesaikan satu proyek praktis sampai tuntas untuk membangun kebiasaan eksekusi.",
            "Latih empati lewat mendengarkan aktif tanpa segera menganalisis.",
            "Berikan dirimu jeda dari analisis bila perasaan lebih membutuhkan perhatian.",
            "Terima bahwa ketidakpastian emosional bukanlah kegagalan berpikir.",
            "Carilah teman yang mampu menyeimbangkan pembicaraan logika dengan kehangatan."
        ),
        detailImages = listOf(
            R.drawable.intpmain,
            R.drawable.intpdesc1,
            R.drawable.intpdesc2
        )
    ),

    "ENTJ" to PersonalityDetails(
        typeName = "ENTJ - Sang Komandan",
        description = "Ia seorang pemimpin yang melihat jalur maju di tengah kebingungan. Ketegasan dan rasa tanggung jawabnya membuatnya sigap mengambil kendali ketika arah perlu ditetapkan. Ia menyukai tantangan, menetapkan tujuan yang besar, dan menata orang serta sumber daya untuk mencapai visi tersebut.",
        strengths = listOf(
            "Kepemimpinan yang tegas dan jelas dalam pengambilan keputusan",
            "Kemampuan memetakan strategi jangka panjang",
            "Efisien dan berorientasi hasil",
            "Percaya diri dan mampu menggerakkan orang lain",
            "Terampil merancang struktur organisasi yang efektif",
            "Bersikap langsung sehingga mengurangi ambiguitas"
        ),
        weaknesses = listOf(
            "Sering terkesan mendominasi dan kurang sabar terhadap proses lambat",
            "Kurang peka terhadap perasaan individu",
            "Sulit menerima kritik yang dipandang tidak konstruktif",
            "Cenderung menuntut standar tinggi yang mungkin memberatkan orang lain",
            "Berisiko menjadi terlalu fokus pada tujuan hingga mengabaikan kesejahteraan",
            "Bisa terlihat dingin saat berusaha menjaga objektivitas"
        ),
        careerPaths = listOf(
            "CEO atau eksekutif perusahaan",
            "Manajer proyek besar atau direktur operasional",
            "Konsultan manajemen atau strategi bisnis",
            "Pengacara korporat atau penegak kebijakan",
            "Wirausaha startup dengan visi skala besar",
            "Pemimpin tim R&D atau kepala departemen"
        ),
        relationships = "Dalam hubungan, ia menunjukkan cinta lewat komitmen dan aksi yang konkret: menetapkan arah, membantu pasangan mencapai tujuan, dan melindungi kestabilan hubungan. Ia menghargai keterbukaan, kompetensi, dan rasa hormat. Namun, ketika stres, ia bisa menuntut terlalu banyak dan sulit menanggapi kebutuhan emosional yang halus. Pasangan yang cocok adalah seseorang yang percaya diri, mampu berdialog langsung, dan juga mampu mengingatkannya untuk merawat sisi lembut dalam kehidupan bersama.",
        developmentTips = listOf(
            "Latih mendengar tanpa segera memberi solusi; kadang orang hanya butuh didengar.",
            "Sisihkan waktu untuk kualitas hubungan tanpa agenda produktif.",
            "Berikan pujian dan pengakuan secara eksplisit kepada orang yang bekerja denganmu.",
            "Belajarlah menunjukkan kerentanan sebagai bentuk kekuatan sosial.",
            "Praktikkan empati aktif: baca bahasa tubuh, bukan hanya kata.",
            "Ingat bahwa hasil terbaik sering lahir dari tim yang merasa dihargai."
        ),
        detailImages = listOf(
            R.drawable.entjmain,
            R.drawable.entjdesc1,
            R.drawable.entjdesc2,
            R.drawable.entjdesc3
        )
    ),

    "ENTP" to PersonalityDetails(
        typeName = "ENTP - Sang Pendebat",
        description = "Ia seorang pencari kemungkinan yang senang memutar argumen demi menguji gagasan. Ia menyukai stimulasi intelektual dan tantangan mental, serta cepat menangkap celah logika dan alternatif solusi. Kegemarannya pada inovasi membuatnya sering muncul dengan ide-ide tak terduga.",
        strengths = listOf(
            "Cepat berpikir dan lincah beradaptasi pada ide baru",
            "Pandai melihat peluang dan menghubungkan gagasan",
            "Karismatik dalam diskusi dan presentasi",
            "Berani menantang asumsi dan norma",
            "Kreatif dalam problem solving yang out-of-the-box",
            "Fleksibel dan tidak terikat pada satu cara berpikir"
        ),
        weaknesses = listOf(
            "Sulit menyelesaikan proyek yang memerlukan konsistensi jangka panjang",
            "Mudah bosan dengan rutinitas dan detail yang membosankan",
            "Kadang terlihat provokatif demi kesenangan intelektual",
            "Kurang sabar terhadap prosedur yang kaku",
            "Terkadang mengabaikan perasaan orang yang terlibat dalam debat",
            "Cenderung menunda implementasi demi terus mencari ide"
        ),
        careerPaths = listOf(
            "Pengusaha inovatif atau pendiri startup",
            "Konsultan kreatif atau inovasi produk",
            "Pengacara litigasi atau advokat argumen",
            "Jurnalis investigasi atau penulis ide-ide provokatif",
            "Pemasaran strategis atau periklanan",
            "Penyiar, host, atau pembicara publik"
        ),
        relationships = "Dalam relasi, ia menyukai pasangan yang dapat terlibat dalam percakapan tajam dan humor intelektual. Ia menunjukkan kasih lewat stimulasi mental dan ide bersama, bukan selalu lewat romansa tradisional. Kadang ia menimbulkan kegelisahan ketika terus menggali argumen; pasangan yang cocok adalah yang menikmati ritme itu, sekaligus mampu menetapkan batas ketika kebutuhan emosional harus didahulukan.",
        developmentTips = listOf(
            "Berusahalah menyelesaikan beberapa ide menjadi bentuk nyata sebelum melompat ke ide berikutnya.",
            "Perhatikan bahasa tubuh pasangan—kadang diam berarti terluka.",
            "Latih komitmen kecil dan konsisten untuk membangun kebiasaan jangka panjang.",
            "Gunakan kemampuan debatmu untuk membangun, bukan merobohkan kepercayaan.",
            "Sisihkan waktu berkualitas tanpa diskusi—cukup hadir dan berbagi.",
            "Belajarlah merayakan proses, bukan hanya hiburan intelektual."
        ),
        detailImages = listOf(
            R.drawable.entpmain,
            R.drawable.entpdesc1,
            R.drawable.entpdesc2
        )
    ),

    // --- DIPLOMAT ---
    "INFJ" to PersonalityDetails(
        typeName = "INFJ - Sang Advokat",
        description = "Ia seorang yang menatap jauh melampaui permukaan. Dalam dirinya mengalir hasrat untuk memahami makna di balik setiap peristiwa, serta kerinduan untuk membawa kebaikan yang sunyi namun nyata. Ia memadukan ketenangan dengan keteguhan, sering kali menjadi tempat aman bagi orang lain meski hatinya sendiri penuh badai yang ia redam dalam diam.",
        strengths = listOf(
            "Empati mendalam terhadap perasaan dan motivasi orang lain",
            "Memiliki visi jangka panjang yang berorientasi nilai",
            "Setia dan berkomitmen pada prinsip yang dianut",
            "Kemampuan membimbing secara tenang dan suportif",
            "Kreatif dalam merancang gagasan bermakna",
            "Berwibawa secara halus, mampu menginspirasi tanpa memaksa"
        ),
        weaknesses = listOf(
            "Mudah terbebani oleh emosi orang lain hingga kelelahan",
            "Cenderung menyembunyikan beban batin daripada berbicara",
            "Perfeksionis terhadap ideal yang sulit dipenuhi",
            "Sulit menerima kekecewaan ketika realita tak sejalan dengan nilai",
            "Enggan berkonfrontasi meski perlu demi kebaikan bersama",
            "Kadang merasa terasing ketika idealismenya tak dipahami"
        ),
        careerPaths = listOf(
            "Konselor, terapis, atau pekerja sosial",
            "Penulis, jurnalis, atau pembuat konten bermakna",
            "Guru atau pendidik yang membimbing secara holistik",
            "Perencana program nirlaba atau aktivis sosial",
            "Psikolog atau peneliti kemanusiaan",
            "Fasilitator komunitas atau penasihat spiritual"
        ),
        relationships = "Ia mencari hubungan yang mendalam dan bermakna—lebih suka kualitas daripada kuantitas. Cinta baginya adalah ruang untuk berbagi nilai, mimpi, dan ketakutan terdalam. Ia memberi perhatian pada detail kecil yang menunjukkan kepedulian, dan berharap pasangannya juga mampu membaca bahasa batinnya. Ketika terluka, ia mudah menarik diri dan menutup diri; ia butuh pasangan yang sabar, konsisten, dan bersedia membangun kepercayaan secara bertahap. Di sisi terbaiknya, ia menjadi pendamping yang setia, penuh pemahaman, dan sanggup menciptakan makna bersama.",
        developmentTips = listOf(
            "Izinkan dirimu berbagi beban—keterbukaan bukan tanda kelemahan, melainkan kekuatan bersama.",
            "Terima bahwa dunia tidak selalu akan serupa dengan idealmu; fleksibilitas memberi ruang bagi dampak nyata.",
            "Jadwalkan waktu untuk memulihkan energi tanpa merasa bersalah.",
            "Latih komunikasi langsung ketika ada rasa kecewa, agar beban tak menumpuk dalam diam.",
            "Berikan penghargaan pada dirimu sendiri atas kerja emosional yang telah dilakukan.",
            "Carilah komunitas kecil yang mendukung nilai-nilai inti yang kau pegang."
        ),
        detailImages = listOf(
            R.drawable.infjmain,
            R.drawable.infjdesc1,
            R.drawable.infjdesc2,
            R.drawable.infjdesc3
        )
    ),

    "INFP" to PersonalityDetails(
        typeName = "INFP - Sang Mediator",
        description = "Ia seorang yang hatinya penuh ideal dan kasih sayang; ia hidup untuk prinsip dan makna. Dunia batinnya kaya akan imajinasi, dan ia sering bertanya: 'Apa yang benar-benar berarti?' Ia cenderung menilai segala sesuatu dari nilai moralnya dan mencari keselarasan antara tindakan dan hati nurani.",
        strengths = listOf(
            "Kepedulian dan empati yang tulus terhadap orang lain",
            "Kreatif dan kaya imajinasi",
            "Setia pada nilai-nilai pribadi dan tujuan bermakna",
            "Mampu melihat potensi baik dalam orang dan situasi",
            "Bersikap fleksibel dan terbuka terhadap perubahan yang bermakna",
            "Berkomitmen pada hal-hal yang memberi makna batin"
        ),
        weaknesses = listOf(
            "Seringkali terlalu idealis sehingga kecewa pada kenyataan",
            "Cenderung menginternalisasi kritik dan merasa terluka",
            "Sulit menindaklanjuti gagasan menjadi hal praktis",
            "Kadang menghindari konfrontasi demi menjaga harmoni",
            "Mudah merasa tidak dimengerti atau terasing",
            "Rentan pada mood swings saat realita tak sesuai harapan"
        ),
        careerPaths = listOf(
            "Penulis kreatif atau seniman yang mengekspresikan emosi",
            "Psikolog atau konselor yang memberi ruang bagi cerita pribadi",
            "Desainer grafis atau illustrator yang berbasis ekspresi",
            "Pekerja organisasi nirlaba yang berfokus pada nilai",
            "Editor, kurator, atau perancang pengalaman bermakna",
            "Guru seni atau fasilitator kreativitas"
        ),
        relationships = "Dalam cinta, ia mencari keaslian—hubungan yang membiarkan kedua jiwa tumbuh tanpa menekan satu sama lain. Ia memberi perhatian dengan cara yang lembut: catatan kecil, pengertian diam, dan dukungan terhadap impian pasangannya. Namun ketika idealismenya terganggu, ia rentan mundur dan mengevaluasi apakah hubungan tersebut selaras dengan nilai-nilainya. Pasangan yang terbaik adalah yang menghormati kedalaman hatinya, berbicara dengan lembut, dan bersedia membangun dunia kecil yang penuh arti bersama.",
        developmentTips = listOf(
            "Ubah ideal besar menjadi langkah kecil yang bisa dilakukan setiap hari.",
            "Belajar menerima kritik sebagai peluang, bukan serangan pada identitas.",
            "Latih menyuarakan kebutuhan secara langsung agar tidak menumpuk dalam hati.",
            "Berikan ruang bagi rutinitas yang membantu ide-ide tumbuh jadi karya nyata.",
            "Tanamkan praktik self-care yang konkret untuk merawat emosi yang rapuh.",
            "Cari teman kreatif yang mendukung implementasi ide, bukan hanya memujinya."
        ),
        detailImages = listOf(
            R.drawable.infpmain,
            R.drawable.infpdesc1,
            R.drawable.infpdesc2
        )
    ),

    "ENFJ" to PersonalityDetails(
        typeName = "ENFJ - Sang Protagonis",
        description = "Ia seorang yang memancarkan kehangatan dan dorongan untuk menggerakkan orang lain ke arah kebaikan. Kepeduliannya terasa nyata; ia sering menjadi katalis bagi perubahan positif di lingkungan sekitarnya. Ia piawai membaca kebutuhan kelompok dan menyatukan orang di bawah visi bersama.",
        strengths = listOf(
            "Karismatik dan mampu menginspirasi orang lain",
            "Sangat peka terhadap kebutuhan emosional orang di sekitarnya",
            "Komunikatif dan pandai membangun hubungan",
            "Pemimpin yang suportif dan memotivasi tim",
            "Peka terhadap dinamika sosial serta mampu memfasilitasi kompromi",
            "Berorientasi pada perkembangan orang lain"
        ),
        weaknesses = listOf(
            "Mudah kelelahan karena selalu memprioritaskan orang lain",
            "Rentan mencari validasi melalui persetujuan sosial",
            "Kadang menekan kebutuhan pribadinya demi menjaga harmoni",
            "Sulit menerima konflik meski kadang perlu untuk pertumbuhan",
            "Bisa terlalu terlibat emosional hingga kehilangan objektivitas",
            "Berisiko kecewa ketika orang yang dicintai tak memenuhi harapan"
        ),
        careerPaths = listOf(
            "Guru, fasilitator, atau mentor pengembangan pribadi",
            "Manajer SDM atau pemimpin tim yang peduli kesejahteraan staf",
            "Diplomat atau pekerja komunitas yang mengorganisir orang",
            "Konselor atau pelatih kehidupan (life coach)",
            "Event organizer yang fokus pada pengalaman kolektif",
            "Pekerja sosial atau advokat pendidikan"
        ),
        relationships = "Ia mencintai dengan keterlibatan penuh—mendengarkan, memotivasi, dan membantu pasangannya tumbuh. Baginya, hubungan adalah ruang perkembangan bersama, bukan hanya kenyamanan. Ia memberi dukungan emosional yang kuat, namun kadang lupa merawat kebutuhan pribadinya karena sibuk menjaga kebahagiaan orang lain. Ia mencari pasangan yang jujur, terbuka, dan mau bersama-sama menghadapi tantangan dengan empati serta tanggung jawab.",
        developmentTips = listOf(
            "Sisihkan waktu untuk dirimu sendiri tanpa merasa bersalah—kamu juga butuh diisi ulang.",
            "Latih menerima ketidaksempurnaan orang lain tanpa perlu segera memperbaiki.",
            "Belajarlah meminta bantuan ketika beban terasa terlalu berat.",
            "Tetapkan batas sehat agar kontribusimu tidak menjadi pengorbanan unik setiap waktu.",
            "Nilai pencapaian kecilmu sebagaimana kamu menghargai orang lain.",
            "Rajut hubungan yang juga memberi ruang bagi kebutuhan pribadimu."
        ),
        detailImages = listOf(
            R.drawable.enfjmain,
            R.drawable.enfjdesc1,
            R.drawable.enfjdesc2
        )
    ),

    "ENFP" to PersonalityDetails(
        typeName = "ENFP - Sang Juru Kampanye",
        description = "Ia seorang yang berdenyut penuh antusiasme dan rasa ingin tahu. Segala hal mengandung kemungkinan; ia menikmati menjelajahi hubungan, ide, dan pengalaman baru. Kegembiraannya menular dan sering menghidupkan suasana di sekelilingnya.",
        strengths = listOf(
            "Energi tinggi dan kemampuan membangkitkan semangat orang lain",
            "Kreatif dan penuh imajinasi dalam melihat peluang baru",
            "Kemampuan berkomunikasi yang hangat dan meyakinkan",
            "Mudah menyesuaikan diri pada situasi sosial",
            "Berorientasi pada makna dan motivasi orang lain",
            "Berani memulai hal baru dan berinovasi"
        ),
        weaknesses = listOf(
            "Seringkali sulit fokus pada satu ide hingga selesai",
            "Rentan stres bila harus menghadapi urutan tugas yang monoton",
            "Kadang menunda hal yang perlu konsistensi jangka panjang",
            "Terbawa emosi dan bisa berubah suasana hati secara cepat",
            "Cenderung menghindari konflik yang mengganggu suasana",
            "Mudah kecewa jika ide-ide tidak mendapat respons yang diharapkan"
        ),
        careerPaths = listOf(
            "Kreator konten, penulis, atau jurnalis kreatif",
            "Pekerja komunitas, aktivis, atau fasilitator kelompok",
            "Konsultan kreativitas atau pemasaran ide",
            "Aktor, presenter, atau profesi publik yang ekspresif",
            "Pengusaha di bidang budaya atau hiburan",
            "Coach atau mentor yang menginspirasi perubahan"
        ),
        relationships = "Ia mencintai dengan antusiasme: penuh perhatian, spontan, dan ingin berbagi pengalaman. Ia mencari pasangan yang bisa menjadi sahabat sekaligus pendukung ide. Namun, ia juga butuh pasangan yang membantu menanamkan struktur ketika gairahnya berfluktuasi. Ia merasa hidup saat hubungannya penuh rasa ingin tahu bersama, tawa, dan eksplorasi—dan ia akan berkontribusi dengan memberi ruang bagi pertumbuhan batin pasangannya.",
        developmentTips = listOf(
            "Belajarlah menyelesaikan sedikit demi sedikit agar ide jadi nyata.",
            "Praktikkan rutinitas kecil untuk menopang kreativitas jangka panjang.",
            "Berikan batas agar energimu tak terkuras oleh ekspektasi orang lain.",
            "Latih hadir dalam hubungan saat pasangan butuh konsistensi, bukan hanya semangat.",
            "Sisihkan waktu untuk refleksi pribadi di luar keramaian ide.",
            "Gunakan jaringan sosialmu sebagai sumber dukungan, bukan hanya panggung."
        ),
        detailImages = listOf(
            R.drawable.enfpmain,
            R.drawable.enfpdesc1,
            R.drawable.enfpdesc2
        )
    ),

    // --- PENJAGA ---
    "ISTJ" to PersonalityDetails(
        typeName = "ISTJ - Sang Ahli Logistik",
        description = "Ia seorang yang menepati janji dan menghargai struktur. Ketelitian dan rasa tanggung jawab adalah wataknya; dunia yang teratur memberinya rasa aman. Ia cenderung menjalani tugas dengan teliti dan berpegang pada fakta serta pengalaman yang terbukti.",
        strengths = listOf(
            "Sangat bertanggung jawab dan dapat diandalkan",
            "Teliti serta memperhatikan detail penting",
            "Konsisten dalam menjalankan tugas dan kewajiban",
            "Memiliki etika kerja yang kuat dan disiplin",
            "Berpegang pada tradisi dan prosedur yang terbukti",
            "Mampu menyelesaikan tugas rutin dengan efektif"
        ),
        weaknesses = listOf(
            "Kurang fleksibel terhadap perubahan mendadak",
            "Kadang menilai solusi baru tanpa memberi kesempatan",
            "Cenderung kaku dalam berpikir ketika situasi butuh inovasi",
            "Sulit mengekspresikan kebutuhan emosional secara langsung",
            "Berisiko terlalu keras pada diri sendiri saat gagal",
            "Mudah frustrasi terhadap ketidakteraturan di sekitar"
        ),
        careerPaths = listOf(
            "Akuntan, auditor, atau analis keuangan",
            "Inspektur, manajer operasi, atau administrator publik",
            "Profesional di bidang logistik dan rantai pasok",
            "Analis data atau quality control specialist",
            "Militer atau kepolisian yang memerlukan disiplin tinggi",
            "Manajer proyek yang memelihara kelancaran operasional"
        ),
        relationships = "Ia menunjukkan cinta lewat keandalan: hadir pada waktu yang dijanjikan, menyelesaikan tanggung jawab, dan menyediakan dukungan praktis. Ia mungkin tidak romantis berlebihan, namun keteguhan dan konsistensinya memberi rasa aman. Ia menghargai pasangan yang dapat menghormati komitmen dan nilai-nilai stabilitas. Dalam konflik, ia cenderung menyelesaikan masalah lewat tindakan konkret daripada retorika emosional.",
        developmentTips = listOf(
            "Cobalah memberi ruang untuk fleksibilitas dalam hal kecil agar tak terasa tertutup pada perubahan.",
            "Latih komunikasi kebutuhan emosional—kata sederhana bisa memperkaya hubungan.",
            "Terima bahwa kreativitas kadang muncul dari ketidakpastian; beri kesempatan ide baru.",
            "Berikan pujian pada dirimu atas keberhasilan kecil, bukan hanya hasil besar.",
            "Praktikkan delegasi untuk mengurangi beban dan memberi kesempatan pada orang lain.",
            "Pelajari teknik manajemen stres agar kerja keras tak berubah menjadi beban kronis."
        ),
        detailImages = listOf(
            R.drawable.istjmain,
            R.drawable.istjdesc1,
            R.drawable.istjdesc2
        )
    ),

    "ISFJ" to PersonalityDetails(
        typeName = "ISFJ - Sang Pembela",
        description = "Ia seorang yang hangat, penuh perhatian, dan berdedikasi pada orang-orang yang ia sayangi. Ia melihat kebutuhan praktis serta emosional orang lain dan berusaha memenuhinya dengan tanggung jawab dan kesetiaan yang teguh.",
        strengths = listOf(
            "Sangat suportif dan setia pada orang terdekat",
            "Peka terhadap kebutuhan praktis dan emosional orang lain",
            "Teliti dan konsisten dalam melaksanakan tugas",
            "Sabar serta kuat dalam menjaga rutinitas sehari-hari",
            "Cenderung bertindak demi kesejahteraan orang lain",
            "Mampu membangun lingkungan yang nyaman dan aman"
        ),
        weaknesses = listOf(
            "Mudah mengorbankan kebutuhan pribadi demi orang lain",
            "Kurang nyaman dengan perubahan besar atau mendadak",
            "Cenderung menahan keluhan hingga menumpuk",
            "Sering merasa tidak pantas menerima pujian",
            "Sulit menetapkan batas apabila diminta terus-menerus",
            "Kadang ragu mengambil pilihan yang melanggar norma"
        ),
        careerPaths = listOf(
            "Perawat, bidan, atau profesi kesehatan lainnya",
            "Guru sekolah dasar atau pendidik anak",
            "Pekerja sosial atau konselor komunitas",
            "Desainer interior yang menciptakan kenyamanan",
            "Pustakawan atau pengelola arsip yang teliti",
            "Manajer administrasi yang menjaga keseharian organisasi"
        ),
        relationships = "Ia mencintai lewat tindakan: perhatian kecil, ingatan pada detail kehidupan, dan usaha praktis untuk membuat pasangan merasa nyaman. Ia mencari stabilitas emosional dan kesetiaan. Ketika merasa diabaikan, ia cenderung menutup diri dan menanggung sendiri. Pasangan yang ideal menghargai pengorbanannya dan membantu ia menetapkan batas agar tak terus memberi hingga kelelahan.",
        developmentTips = listOf(
            "Pelajari cara mengatakan 'tidak' dengan lembut ketika kapasitasmu terbatas.",
            "Sisihkan waktu untuk kebutuhan pribadimu yang tak kalah penting.",
            "Terima pujian sebagai cerminan nilai usaha, bukan sekadar basa-basi.",
            "Berlatih membicarakan perasaan sebelum menumpuk menjadi beban.",
            "Buka sedikit ruang untuk hal baru agar fleksibilitas tumbuh perlahan.",
            "Bangun komunitas kecil yang mendukung kebutuhanmu, bukan hanya orang yang dibantu."
        ),
        detailImages = listOf(
            R.drawable.isfjmain,
            R.drawable.isfjdesc1,
            R.drawable.isfjdesc2
        )
    ),

    "ESTJ" to PersonalityDetails(
        typeName = "ESTJ - Sang Eksekutif",
        description = "Ia seorang yang tangguh, terorganisir, dan mengutamakan praktik yang terbukti. Ia menemukan kenyamanan dalam struktur dan kepastian, dan sering menjadi penggerak yang memastikan segala sesuatunya berjalan sesuai rencana.",
        strengths = listOf(
            "Kemampuan manajerial dan organisasi yang kuat",
            "Tegas serta konsisten dalam penerapan aturan",
            "Bertanggung jawab dan dapat diandalkan dalam tugas besar",
            "Pragmatis dan fokus pada hasil nyata",
            "Mampu memimpin tim dengan jelas dan efektif",
            "Berorientasi pada tanggung jawab sosial dan profesional"
        ),
        weaknesses = listOf(
            "Kurang fleksibel terhadap pendekatan baru yang tidak teruji",
            "Bisa terlalu menghakimi jika orang tak sesuai standar",
            "Sulit mengungkapkan kelembutan emosional",
            "Rentan mengutamakan kewajiban hingga relasi pribadi tertekan",
            "Kadang mengabaikan perasaan individu demi sistem",
            "Mudah merasa terganggu oleh ketidakteraturan"
        ),
        careerPaths = listOf(
            "Manajer operasional atau direktur perusahaan",
            "Hakim, pengacara, atau pejabat administrasi",
            "Manajer proyek dan kepala unit eksekusi",
            "Perwira militer atau posisi dengan struktur hierarki",
            "Analis keuangan atau manajer risiko",
            "Kepala unit produksi atau pengelola fasilitas"
        ),
        relationships = "Ia menunjukkan cinta dengan stabilitas: komitmen yang jelas, perencanaan masa depan, dan tanggung jawab yang konsisten. Ia menghargai pasangan yang menghormati nilai kerja keras dan keteraturan. Namun ia perlu belajar menampilkan kelembutan agar hubungan tak terasa semata kontrak. Hubungan ideal adalah yang menjadikan rumah sebagai tempat teratur namun penuh kehangatan nyata.",
        developmentTips = listOf(
            "Berlatih memberi ruang pada emosi yang tak logis—itu bagian dari hidup manusiawi.",
            "Cobalah mengekspresikan apresiasi dalam kata-kata, bukan hanya tindakan.",
            "Rilekskan kendali pada hal-hal kecil untuk menjaga keseimbangan batin.",
            "Pelajari perspektif lain sebelum memutuskan agar keputusan lebih inklusif.",
            "Sisihkan waktu tanpa agenda kerja untuk mempererat hubungan personal.",
            "Terima ketidaksempurnaan sebagai bagian dari proses pembelajaran bersama."
        ),
        detailImages = listOf(
            R.drawable.estjmain,
            R.drawable.estjdesc1,
            R.drawable.estjdesc2
        )
    ),

    "ESFJ" to PersonalityDetails(
        typeName = "ESFJ - Sang Konsul",
        description = "Ia seorang yang ramah, peduli, dan terampil menjaga keharmonisan sosial. Ia mendapat kepuasan ketika membantu orang lain merasa diterima dan nyaman. Perhatiannya kepada detail hubungan membuatnya mudah menjadi pusat dukungan sosial.",
        strengths = listOf(
            "Kemampuan sosial yang kuat dan penuh perhatian",
            "Sangat setia dan peduli pada orang terdekat",
            "Terorganisir dalam menjaga acara dan hubungan",
            "Peka terhadap norma sosial dan empati praktis",
            "Mudah membangun jaringan dan menghubungkan orang",
            "Berorientasi pada pelayanan dan kesejahteraan kelompok"
        ),
        weaknesses = listOf(
            "Sering mencari persetujuan eksternal hingga tergantung validasi",
            "Cenderung mengorbankan kebutuhan pribadi demi orang lain",
            "Mudah tersinggung oleh kritik yang dianggap pribadi",
            "Kurang nyaman dengan perubahan yang mengganggu rutinitas sosial",
            "Kadang terlalu fokus pada citra sosial daripada isi hati",
            "Sulit menolak permintaan yang menambah beban emosional"
        ),
        careerPaths = listOf(
            "Event planner atau manajer hubungan masyarakat",
            "Perawat, guru, atau pekerjaan layanan publik",
            "Manajer SDM atau konsultan kesejahteraan karyawan",
            "Pekerja organisasi komunitas atau relawan koordinatif",
            "Customer relations atau layanan pelanggan tingkat tinggi",
            "Konselor pendidikan atau pembimbing karier"
        ),
        relationships = "Ia memberi cinta lewat perhatian dan pelayanan: merawat, mengingat tanggal penting, menyiapkan hal-hal kecil yang membuat pasangan merasa aman. Ia membutuhkan penghargaan yang jelas; bila diabaikan, ia mudah merasa terluka. Hubungan idealnya adalah yang stabil, penuh ritual kelembutan, dan saling mendukung peran tanggung jawab dalam kehidupan sehari-hari.",
        developmentTips = listOf(
            "Belajarlah menetapkan batas agar memberi tidak berubah menjadi beban.",
            "Terima bahwa penolakan kadang sehat dan bukan cermin nilai dirimu.",
            "Luangkan waktu untuk perawatan diri tanpa rasa bersalah.",
            "Cari pasangan yang mampu memberi apresiasi verbal selain tindakanmu.",
            "Berlatih menerima kritik sebagai alat berkembang, bukan serangan personal.",
            "Kembangkan rutinitas yang juga memprioritaskan kebutuhanmu sendiri."
        ),
        detailImages = listOf(
            R.drawable.esfjmain,
            R.drawable.esfjdesc1,
            R.drawable.esfjdesc2
        )
    ),

    // --- PENJELAJAH ---
    "ISTP" to PersonalityDetails(
        typeName = "ISTP - Sang Virtuoso",
        description = "Ia seorang yang tangkas dengan dunia konkret: memecahkan masalah praktis, mengutak-atik alat, dan beradaptasi cepat saat situasi berubah. Ia menikmati keterampilan tangan dan kebebasan untuk bereksperimen tanpa terlalu banyak batasan.",
        strengths = listOf(
            "Keterampilan praktis dan kemampuan memecahkan masalah teknis",
            "Tenang dalam situasi krisis dan keputusan cepat",
            "Adaptif dan spontan saat menghadapi hal baru",
            "Fleksibel dan tak terikat pada rutinitas kaku",
            "Berani mencoba pengalaman fisik yang menantang",
            "Cakap mengubah ide menjadi tindakan langsung"
        ),
        weaknesses = listOf(
            "Kurang sabar pada struktur yang kaku atau administratif",
            "Sulit berkomitmen pada proyek yang panjang tanpa hasil cepat",
            "Cenderung menahan emosi hingga meledak mendadak",
            "Kadang mengambil risiko berlebihan tanpa pertimbangan panjang",
            "Kurang peka terhadap kebutuhan emosional jangka panjang pasangan",
            "Mudah bosan dengan teori tanpa aplikasi praktis"
        ),
        careerPaths = listOf(
            "Mekanik, pilot, atau teknisi lapangan",
            "Insinyur lapangan atau ahli perawatan mesin",
            "Paramedis atau pekerja darurat dengan keterampilan praktis",
            "Programmer yang suka menyusun solusi teknis langsung",
            "Ahli forensik atau teknisi laboratorium terapan",
            "Profesional olahraga atau pelatih aktivitas fisik"
        ),
        relationships = "Ia mendekati hubungan dengan praktikalitas: memberi dukungan lewat tindakan nyata dan kehadiran saat dibutuhkan. Ia menyukai pasangan yang memberi kebebasan dan tak terlalu menuntut kehangatan verbal setiap waktu. Ketika merasa aman, ia dapat sangat setia dan protektif. Ia menghargai keseimbangan antara spontanitas dan kedekatan rutin yang sederhana.",
        developmentTips = listOf(
            "Belajarlah mengekspresikan kebutuhan emosional sebelum menumpuk menjadi masalah.",
            "Pertimbangkan risiko dengan skala waktu yang lebih panjang pada keputusan penting.",
            "Coba terlibat dalam proyek jangka panjang untuk melatih konsistensi.",
            "Sisihkan waktu ngobrol tanpa agenda praktis—biarkan emosi mengalir.",
            "Berbagi tugas emosional dengan pasangan agar beban tak berat sendirian.",
            "Gunakan keterampilan praktismu untuk menciptakan momen kedekatan yang bermakna."
        ),
        detailImages = listOf(
            R.drawable.istpmain,
            R.drawable.istpdesc1,
            R.drawable.istpdesc2
        )
    ),

    "ISFP" to PersonalityDetails(
        typeName = "ISFP - Sang Petualang",
        description = "Ia seorang yang hidup dalam kepekaan terhadap pengalaman indrawi dan keindahan. Ia memilih menjalani hidup lewat perasaan dan ekspresi estetis, menghargai momen dan kebebasan untuk menjadi otentik.",
        strengths = listOf(
            "Berjiwa artistik dan peka terhadap keindahan sekitar",
            "Hangat dan mampu membuat orang merasa nyaman",
            "Fleksibel dan mudah menyesuaikan diri dengan pengalaman baru",
            "Terampil mengekspresikan perasaan lewat karya atau tindakan",
            "Mengejar kebebasan pribadi tanpa menghakimi orang lain",
            "Mampu menghadirkan kegembiraan sederhana dalam kehidupan sehari-hari"
        ),
        weaknesses = listOf(
            "Cenderung menghindari konflik dan pembicaraan emosional berat",
            "Sulit mempertahankan komitmen jangka panjang pada beberapa hal",
            "Rentan stres ketika terjebak pada rutinitas ketat",
            "Kadang menahan emosi hingga membuat keputusan impulsif",
            "Bisa tampak pasif saat situasi menuntut inisiatif berkelanjutan",
            "Mudah terluka oleh kritik terhadap ekspresi kreatifnya"
        ),
        careerPaths = listOf(
            "Seniman, musisi, atau desainer kreatif",
            "Fotografer atau pekerja seni visual",
            "Koki atau pekerja kuliner yang ekspresif",
            "Desainer fesyen atau stylist",
            "Pekerja sosial lapangan yang berfokus pada pengalaman manusia",
            "Pemandu wisata atau instruktur kegiatan alam"
        ),
        relationships = "Ia mencintai lewat kehadiran yang lembut: sentuhan, perhatian pada detail kecil, dan berbagi pengalaman sensorial. Ia menghargai pasangan yang memberi ruang berekspresi dan tak mengekang kebebasannya. Ketika merasa aman, ia penuh kasih dan rela berkorban; namun ketika tertekan, ia cenderung mundur untuk menjaga kestabilan batinnya.",
        developmentTips = listOf(
            "Latih menyampaikan kebutuhan dengan kata-kata, bukan hanya lewat tindakan.",
            "Bangun rutinitas kecil untuk memberi pondasi bagi kreativitas jangka panjang.",
            "Beranilah menghadapi konflik kecil agar hubungan tak menumpuk masalah.",
            "Sediakan waktu pribadi untuk merawat emosi agar tidak meledak impulsif.",
            "Eksplorasi cara menyalurkan ketidaknyamanan lewat seni atau gerak.",
            "Cari komunitas kreatif yang mendukung konsistensi berkarya."
        ),
        detailImages = listOf(
            R.drawable.isfpmain,
            R.drawable.isfpdesc1,
            R.drawable.isfpdesc2
        )
    ),

    "ESTP" to PersonalityDetails(
        typeName = "ESTP - Sang Pengusaha",
        description = "Ia seorang yang berani hidup di garis depan pengalaman—lari dari teori berlebih dan memilih aksi cepat. Ia gesit, pragmatis, dan menikmati tantangan langsung; dunia baginya adalah arena untuk bereaksi dan menangkap peluang.",
        strengths = listOf(
            "Cepat bertindak dan tanggap terhadap kesempatan",
            "Keterampilan praktis dan kemampuan beradaptasi tinggi",
            "Karismatik dalam interaksi sosial dan persuasi",
            "Berani mengambil keputusan saat dibutuhkan",
            "Efektif dalam situasi darurat atau bertekanan",
            "Memiliki keterampilan teknis dan insting praktis"
        ),
        weaknesses = listOf(
            "Cenderung impulsif dan kurang perencanaan jangka panjang",
            "Kurang sabar dengan teori atau diskusi panjang",
            "Mudah bosan dengan tanggung jawab administratif",
            "Berisiko mengambil tindakan tanpa mempertimbangkan konsekuensi",
            "Kadang mengabaikan perasaan yang tak terlihat secara langsung",
            "Sulit mempertahankan komitmen yang monoton"
        ),
        careerPaths = listOf(
            "Pengusaha lapangan atau pemilik usaha kecil",
            "Sales handal, wirausaha, atau perwakilan bisnis",
            "Paramedis atau profesi tanggap darurat",
            "Detektif, penyidik, atau profesi lapangan lainnya",
            "Atlet profesional atau instruktur olahraga",
            "Pilot atau profesi yang menuntut refleks dan ketangkasan"
        ),
        relationships = "Ia memaknai hubungan lewat dinamika, aksi, dan pengalaman bersama. Ia menyukai pasangan yang spontan dan berani mencoba hal baru. Ia menunjukkan cinta lewat petualangan dan dukungan praktis—menjadi rekan dalam pengalaman sehari-hari. Namun, untuk kestabilan jangka panjang ia perlu belajar merencanakan dan konsisten pada komitmen yang tak selalu seru.",
        developmentTips = listOf(
            "Latih menilai konsekuensi jangka panjang sebelum bertindak impulsif.",
            "Pelajari cara menikmati rutinitas kecil sebagai pondasi keintiman.",
            "Berlatih komunikasi perasaan ketika pasangan butuh dukungan emosi.",
            "Sisihkan waktu untuk perencanaan finansial dan tujuan bersama.",
            "Cari kegiatan bersama yang menyalurkan energi positif tanpa risiko berlebih.",
            "Belajarlah menepati janji kecil—konsistensi membangun kepercayaan."
        ),
        detailImages = listOf(
            R.drawable.estpmain,
            R.drawable.estpdesc1,
            R.drawable.estpdesc2
        )
    ),

    "ESFP" to PersonalityDetails(
        typeName = "ESFP - Sang Penghibur",
        description = "Ia seorang yang hidup untuk berbagi kegembiraan. Kehadirannya membuat suasana lebih ringan dan hangat; ia pandai menemukan kesenangan di hal-hal sederhana dan membuat orang lain merasa diikutsertakan.",
        strengths = listOf(
            "Membawa energi positif dan kemampuan menghibur orang lain",
            "Mudah berbaur dan membangun keakraban cepat",
            "Peka pada pengalaman indrawi dan estetika",
            "Cepat bertindak dan memanfaatkan momen",
            "Optimis dan membantu mengangkat suasana hati orang lain",
            "Praktis dalam menangani hal-hal yang bersifat sosial"
        ),
        weaknesses = listOf(
            "Kurang sabar untuk rencana jangka panjang dan teori",
            "Mudah terganggu dan kehilangan fokus pada tujuan panjang",
            "Cenderung menghindari masalah emosional yang serius",
            "Memerlukan validasi sosial yang berlebihan kadang kala",
            "Sering menunda tugas yang kurang menyenangkan",
            "Rentan stres saat sensasi dan hiburan berkurang"
        ),
        careerPaths = listOf(
            "Aktor, entertainer, atau performer panggung",
            "Event planner atau public relations",
            "Pemandu wisata atau fasilitator pengalaman",
            "Desainer visual atau penata gaya yang sensitif estetika",
            "Pekerja pelayanan yang membutuhkan kehangatan interpersonal",
            "Konsultan event lifestyle atau brand ambassador"
        ),
        relationships = "Ia memberi cinta lewat kehadiran riang: merencanakan kejutan, membawa keceriaan, dan membuat momen spesial. Ia butuh pasangan yang mencintai keaktifan dan tak kaku pada rencana. Koeksistensi terbaik baginya adalah saat pasangan bersedia ikut merasakan petualangan, namun juga menempatkan batas saat diperlukan demi kestabilan emosi.",
        developmentTips = listOf(
            "Berlatih membangun komitmen kecil yang mendukung tujuan jangka panjang.",
            "Sediakan waktu untuk refleksi pribadi di luar keramaian.",
            "Belajar menghadapi percakapan serius tanpa mengalihkan topik.",
            "Peliharalah keuangan dan rencana ke depan agar kebebasan tetap berkelanjutan.",
            "Terima bahwa keintiman kadang butuh diam dan konsistensi, bukan hanya aksi.",
            "Cari keseimbangan antara memberi kegembiraan dan menjaga kestabilan."
        ),
        detailImages = listOf(
            R.drawable.esfpmain,
            R.drawable.esfpdesc1,
            R.drawable.esfpdesc2
        )
    )
)
