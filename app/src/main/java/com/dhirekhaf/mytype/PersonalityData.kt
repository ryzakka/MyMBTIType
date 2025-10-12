// Lokasi: app/src/main/java/com/dhirekhaf/mytype/PersonalityData.kt
// [VERSI FINAL - MENYESUAIKAN DENGAN KODE ANDA YANG SUDAH ADA]

package com.dhirekhaf.mytype

import androidx.compose.ui.graphics.Color
import androidx.annotation.DrawableRes

// --- DATA KONTEN AKTUAL ---

data class BingoTrait(val text: String, val span: Int = 1)


// 1. DATA UNTUK TEMA WARNA GRUP
val analystTheme = GroupTheme(Color(0xff735283), Color(0xFFD0BCFF), "735283")
val diplomatTheme = GroupTheme(Color(0xFF3B7A57), Color(0xFFC3F3D0), "3B7A57")
val sentinelTheme = GroupTheme(Color(0xFF004A7F), Color(0xFFA5D8FF), "004A7F")
val explorerTheme = GroupTheme(Color(0xFFB8860B), Color(0xFFFFE082), "B8860B")

// 2. DATA UNTUK TAMPILAN DAFTAR (SATU-SATUNYA DEKLARASI YANG BENAR)
val personalityGroupsForList: List<PersonalityGroup> = listOf(
    PersonalityGroup(
        groupTitle = "Para Analis",
        briefDescription = "Dikenal karena rasionalitas, imparsialitas, dan kecerdasan intelektualnya.",
        idealPartner = "ENFP",
        goodPartner = "INFP",
        groupHeaderImageRes = R.drawable.theanalysts,
        groupBackgroundImageRes = R.drawable.latarbodyungu,
        types = listOf(
            PersonalityInfo("INTJ", "Sang Arsitek", R.drawable.intj),
            PersonalityInfo("INTP", "Sang Ahli Logika", R.drawable.intp),
            PersonalityInfo("ENTJ", "Sang Komandan", R.drawable.entj),
            PersonalityInfo("ENTP", "Sang Pendebat", R.drawable.entp)
        )
    ),
    PersonalityGroup(
        groupTitle = "Para Diplomat",
        briefDescription = "Dikenal karena empati, kemampuan diplomasi, dan semangat idealisnya.",
        idealPartner = "ENTJ",
        goodPartner = "ENFJ",
        groupHeaderImageRes = R.drawable.thediplomats,
        groupBackgroundImageRes = R.drawable.latarbodyhijau,
        types = listOf(
            PersonalityInfo("INFJ", "Sang Advokat", R.drawable.infj),
            PersonalityInfo("INFP", "Sang Mediator", R.drawable.infp),
            PersonalityInfo("ENFJ", "Sang Protagonis", R.drawable.enfj),
            PersonalityInfo("ENFP", "Sang Juru Kampanye", R.drawable.enfp)
        )
    ),
    PersonalityGroup(
        groupTitle = "Para Penjaga",
        briefDescription = "Dikenal karena kepraktisan, keteraturan, dan dedikasinya pada stabilitas.",
        idealPartner = "ESTP",
        goodPartner = "ESFP",
        groupHeaderImageRes = R.drawable.thesentinels,
        groupBackgroundImageRes = R.drawable.latarbodybiru,
        types = listOf(
            PersonalityInfo("ISTJ", "Sang Ahli Logistik", R.drawable.istj),
            PersonalityInfo("ISFJ", "Sang Pembela", R.drawable.isfj),
            PersonalityInfo("ESTJ", "Sang Eksekutif", R.drawable.estj),
            PersonalityInfo("ESFJ", "Sang Konsul", R.drawable.esfj)
        )
    ),
    PersonalityGroup(
        groupTitle = "Para Perajin",
        briefDescription = "Dikenal karena spontanitas, kreativitas praktis, dan kemampuannya beradaptasi.",
        idealPartner = "ISFJ",
        goodPartner = "ISTJ",
        groupHeaderImageRes = R.drawable.theexplorers,
        groupBackgroundImageRes = R.drawable.latarbodykuning,
        types = listOf(
            PersonalityInfo("ISTP", "Sang Virtuoso", R.drawable.istp),
            PersonalityInfo("ISFP", "Sang Petualang", R.drawable.isfp),
            PersonalityInfo("ESTP", "Sang Pengusaha", R.drawable.estp),
            PersonalityInfo("ESFP", "Sang Penghibur", R.drawable.esfp)
        )
    )
)

// 3. DATA UNTUK TAMPILAN DETAIL (digunakan di PersonalityDetailScreen)
val personalityDetailsMap: Map<String, PersonalityDetails> = mapOf(
    // --- ANALIS ---
    "INTJ" to PersonalityDetails(
        typeName = "INTJ - Sang Arsitek",
        description = "Ia seorang yang merancang jalan di peta yang tak kasat mata—selalu menimbang kemungkinan sebelum melangkah. Di kepalanya, dunia adalah struktur yang bisa disusun ulang menjadi sesuatu yang lebih efisien dan bermakna.",
        strengths = listOf("Berpikir strategis", "Mandiri", "Objektif", "Cepat menangkap pola"),
        weaknesses = listOf("Bisa tampak dingin", "Kurang peka emosional", "Perfeksionis", "Sulit menerima otoritas"),
        careerPaths = listOf("Ilmuwan", "Arsitek sistem", "Analis keuangan", "Konsultan kebijakan"),
        relationships = "Dalam hubungan, ia mencintai melalui bentuk dukungan yang konkret: memberi solusi, merencanakan masa depan, dan menjaga komitmen intelektual. Kesetiaannya dalam tindakan sangat kuat.",
        developmentTips = listOf("Latih ungkapan sederhana tentang perasaanmu.", "Jadwalkan waktu tanpa tujuan produktif.", "Tanyakan 'apa yang dibutuhkan?' sebelum memberi solusi."),
        detailImages = listOf(R.drawable.intjmain, R.drawable.intjdesc1, R.drawable.intjdesc2),
        viewsFromOthers = listOf(
            OtherTypeView("INTP", "Menghormati kecerdasan mereka, tapi merasa logika mereka kadang kurang aplikatif."),
            OtherTypeView("ENTJ", "Melihatnya sebagai rekan strategis yang hebat, meski kadang terlalu individualistis."),
            OtherTypeView("ENTP", "Menyukai kemampuan mereka membangun sistem, tapi merasa mereka terlalu kaku."),
            OtherTypeView("INFJ", "Memahami visi mereka, namun berharap mereka lebih hangat secara personal."),
            OtherTypeView("INFP", "Mengagumi kecerdasan dan visi mereka, tapi kadang merasa terintimidasi oleh logika dinginnya."),
            OtherTypeView("ENFJ", "Menghargai kemampuan sosial mereka, tetapi merasa pendekatan mereka seringkali kurang efisien."),
            OtherTypeView("ENFP", "Terkesan dengan kedalaman pikiran mereka, tapi merasa energinya terlalu terkontrol."),
            OtherTypeView("ISTJ", "Menghargai logika mereka, namun merasa visi jangka panjang mereka kadang tidak praktis."),
            OtherTypeView("ISFJ", "Merasa mereka cerdas, tapi sulit memahami apa yang mereka rasakan."),
            OtherTypeView("ESTJ", "Mengakui efisiensi mereka, tapi merasa mereka kurang mempertimbangkan aspek manusia."),
            OtherTypeView("ESFJ", "Menganggap mereka pintar, tapi terlalu serius dan sulit diajak bersantai."),
            OtherTypeView("ISTP", "Menghormati kemandiriannya, tapi merasa mereka terlalu banyak berteori."),
            OtherTypeView("ISFP", "Merasa mereka terlalu analitis dan kurang bisa menikmati momen."),
            OtherTypeView("ESTP", "Menganggap mereka terlalu banyak berpikir, tetapi menghormati kemampuan strategi jangka panjangnya."),
            OtherTypeView("ESFP", "Menganggap mereka brilian, namun terlalu kaku untuk diajak bersenang-senang.")
        ),
        relationshipDetails = listOf(
            RelationshipDetail("INFP",
                strengths = listOf(
                    "Koneksi yang mendalam antara visi dan nilai.",
                    "Saling menginspirasi untuk menjadi versi terbaik diri masing-masing.",
                    "Keduanya menghargai keaslian dan kedalaman emosi."
                ),
                challenges = listOf(
                    "INTJ bisa tampak terlalu logis bagi INFP yang sensitif.",
                    "INFP bisa merasa kurang dipahami saat INTJ terlalu fokus pada rencana.",
                    "Perbedaan cara mengekspresikan kasih sayang dan dukungan emosional."
                ),
                advice = listOf(
                    "INTJ: Beri ruang bagi ekspresi emosional tanpa mencoba langsung memperbaiki.",
                    "INFP: Hargai niat baik di balik ketegasan logika INTJ.",
                    "Temukan keseimbangan antara visi ideal dan langkah nyata untuk mewujudkannya."
                )
            ),

            RelationshipDetail("INTP",
                strengths = listOf(
                    "Keduanya menghargai pemikiran mendalam dan otonomi.",
                    "Diskusi intelektual yang menstimulasi dan penuh ide orisinal.",
                    "Saling memahami kebutuhan ruang pribadi dan fokus pada visi jangka panjang."
                ),
                challenges = listOf(
                    "Kurang ekspresif secara emosional, bisa membuat hubungan terasa dingin.",
                    "INTP bisa tampak terlalu santai bagi INTJ yang berorientasi hasil.",
                    "INTJ bisa terlalu menuntut struktur bagi INTP yang fleksibel."
                ),
                advice = listOf(
                    "INTJ: Hargai spontanitas intelektual INTP tanpa menuntut kesimpulan segera.",
                    "INTP: Beri umpan balik konkret agar INTJ merasa kemajuan nyata tercapai.",
                    "Gunakan logika untuk memahami, bukan untuk memenangkan argumen."
                )
            ),
            RelationshipDetail("ENTP",
                strengths = listOf(
                    "Pertemuan dua pikiran cepat dan inovatif.",
                    "Saling menantang dengan cara yang memperluas wawasan.",
                    "Kombinasi antara visi strategis dan ide spontan."
                ),
                challenges = listOf(
                    "ENTP bisa membuat INTJ frustrasi karena kurang konsistensi.",
                    "INTJ bisa dianggap terlalu kaku atau serius oleh ENTP.",
                    "Keduanya bisa terjebak dalam debat tanpa arah emosional."
                ),
                advice = listOf(
                    "INTJ: Jangan terlalu cepat menilai ide ENTP tidak realistis.",
                    "ENTP: Hargai struktur dan keseriusan INTJ sebagai bagian dari visi.",
                    "Gunakan energi debat untuk membangun, bukan menguras."
                )
            ),

            RelationshipDetail("ENTJ",
                strengths = listOf(
                    "Dua pemimpin strategis yang menghargai efisiensi dan visi besar.",
                    "Kerjasama yang produktif jika nilai dan arah selaras.",
                    "Keduanya berpikir jauh ke depan dan menghargai kompetensi."
                ),
                challenges = listOf(
                    "Benturan ego bisa terjadi karena keduanya kuat dan dominan.",
                    "Kurangnya ekspresi emosi bisa menimbulkan jarak.",
                    "ENTJ mungkin menilai INTJ terlalu lambat dalam mengambil keputusan."
                ),
                advice = listOf(
                    "INTJ: Sampaikan gagasan dengan ketegasan, bukan dengan jarak.",
                    "ENTJ: Belajar menghargai proses reflektif INTJ.",
                    "Bangun kemitraan yang menyeimbangkan kekuatan dan keheningan."
                )
            ),

            RelationshipDetail("INFJ",
                strengths = listOf(
                    "Kedua tipe ini berorientasi visi dan mendalam secara batin.",
                    "Koneksi yang tenang namun penuh makna.",
                    "Saling menghormati integritas dan komitmen pada pertumbuhan pribadi."
                ),
                challenges = listOf(
                    "Komunikasi bisa tertutup karena keduanya introvert dan penuh asumsi.",
                    "INTJ cenderung menekan emosi, INFJ ingin mengekspresikannya.",
                    "Keduanya bisa kehilangan kehangatan karena fokus pada konsep."
                ),
                advice = listOf(
                    "INTJ: Belajar membuka sisi emosional tanpa takut kehilangan kontrol.",
                    "INFJ: Sampaikan perasaan secara langsung tanpa berputar terlalu jauh.",
                    "Gunakan visi bersama sebagai jembatan antara logika dan empati."
                )
            ),

            RelationshipDetail("INFP",
                strengths = listOf(
                    "Koneksi yang mendalam antara visi dan nilai.",
                    "Saling menginspirasi untuk menjadi versi terbaik diri masing-masing.",
                    "Keduanya menghargai keaslian dan kedalaman emosi."
                ),
                challenges = listOf(
                    "INTJ bisa tampak terlalu logis bagi INFP yang sensitif.",
                    "INFP bisa merasa kurang dipahami saat INTJ terlalu fokus pada rencana.",
                    "Perbedaan cara mengekspresikan kasih sayang dan dukungan emosional."
                ),
                advice = listOf(
                    "INTJ: Beri ruang bagi ekspresi emosional tanpa mencoba langsung memperbaiki.",
                    "INFP: Hargai niat baik di balik ketegasan logika INTJ.",
                    "Temukan keseimbangan antara visi ideal dan langkah nyata untuk mewujudkannya."
                )
            ),

            RelationshipDetail("ENFP",
                strengths = listOf(
                    "ENFP membawa spontanitas dan semangat yang menghangatkan INTJ.",
                    "INTJ memberi arah konkret bagi ide-ide ENFP.",
                    "Saling melengkapi antara intuisi kreatif dan struktur logis."
                ),
                challenges = listOf(
                    "Perbedaan ritme energi: ENFP dinamis, INTJ tenang.",
                    "ENFP bisa merasa dibatasi oleh logika INTJ.",
                    "INTJ bisa kewalahan dengan emosi dan impuls ENFP."
                ),
                advice = listOf(
                    "INTJ: Belajar menikmati spontanitas tanpa merasa kehilangan kendali.",
                    "ENFP: Beri waktu bagi INTJ untuk memproses tanpa menekan segera.",
                    "Bangun keseimbangan antara inspirasi dan implementasi."
                )
            ),

            RelationshipDetail("ENFJ",
                strengths = listOf(
                    "Keduanya visioner dan peduli akan pengembangan diri dan orang lain.",
                    "ENFJ membantu INTJ lebih terhubung dengan sisi manusiawi.",
                    "INTJ memberi arah strategis bagi visi sosial ENFJ."
                ),
                challenges = listOf(
                    "ENFJ bisa merasa INTJ terlalu dingin atau tidak peka.",
                    "INTJ bisa merasa ENFJ terlalu emosional atau mendominasi secara sosial.",
                    "Perbedaan fokus: hubungan versus hasil."
                ),
                advice = listOf(
                    "INTJ: Luangkan waktu untuk mendengarkan tanpa langsung mencari solusi.",
                    "ENFJ: Hormati kebutuhan INTJ akan privasi dan refleksi.",
                    "Gunakan empati ENFJ dan kejelasan INTJ sebagai pasangan yang saling menyeimbangkan."
                )
            ),

            RelationshipDetail("ISTJ",
                strengths = listOf(
                    "Keduanya rasional, fokus pada tanggung jawab dan hasil nyata.",
                    "Kerja sama efisien dengan pembagian peran yang jelas.",
                    "Sama-sama menghargai kompetensi dan keandalan."
                ),
                challenges = listOf(
                    "Perbedaan orientasi waktu: ISTJ ke masa lalu, INTJ ke masa depan.",
                    "INTJ bisa menilai ISTJ terlalu kaku, ISTJ menilai INTJ terlalu teoritis.",
                    "Kurangnya fleksibilitas dalam komunikasi emosional."
                ),
                advice = listOf(
                    "INTJ: Hargai kebijaksanaan pengalaman ISTJ.",
                    "ISTJ: Terbuka pada ide-ide baru meski belum terbukti.",
                    "Fokus pada tujuan bersama, bukan cara yang berbeda."
                )
            ),

            RelationshipDetail("ISFJ",
                strengths = listOf(
                    "Hubungan yang stabil dan penuh pengabdian.",
                    "ISFJ membawa kehangatan dan kepedulian yang menenangkan INTJ.",
                    "INTJ memberi arah dan rasa aman terhadap masa depan."
                ),
                challenges = listOf(
                    "INTJ bisa tampak terlalu dingin bagi ISFJ yang berorientasi perasaan.",
                    "ISFJ bisa merasa cemas dengan gaya komunikasi INTJ yang langsung.",
                    "Perbedaan fokus: harmoni sosial vs visi konseptual."
                ),
                advice = listOf(
                    "INTJ: Tunjukkan penghargaan secara nyata, bukan hanya dengan hasil.",
                    "ISFJ: Jangan takut memberi masukan pada INTJ dengan jujur.",
                    "Bangun rutinitas yang menjaga kedekatan emosional dan arah hidup."
                )
            ),

            RelationshipDetail("ESTJ",
                strengths = listOf(
                    "Keduanya pekerja keras, logis, dan berorientasi hasil.",
                    "Dapat menjadi tim yang sangat efektif dalam mencapai tujuan nyata.",
                    "Saling menghormati disiplin dan kompetensi masing-masing."
                ),
                challenges = listOf(
                    "ESTJ bisa menilai INTJ terlalu teoritis atau lamban.",
                    "INTJ bisa menilai ESTJ terlalu memaksa atau kurang reflektif.",
                    "Kurangnya kehangatan emosional dalam hubungan."
                ),
                advice = listOf(
                    "INTJ: Jelaskan ide dalam konteks praktis agar ESTJ mudah memahami.",
                    "ESTJ: Beri waktu bagi INTJ untuk menyusun strategi sebelum bertindak.",
                    "Gunakan kekuatan logika untuk berkolaborasi, bukan berkompetisi."
                )
            ),

            RelationshipDetail("ESFJ",
                strengths = listOf(
                    "ESFJ membawa kehangatan sosial dan perhatian yang tulus.",
                    "INTJ memberi arah rasional dan kestabilan dalam perencanaan.",
                    "Keduanya bisa membangun lingkungan yang aman dan terstruktur."
                ),
                challenges = listOf(
                    "ESFJ bisa merasa INTJ terlalu tertutup dan dingin.",
                    "INTJ bisa kewalahan dengan kebutuhan sosial ESFJ.",
                    "Nilai yang berbeda antara harmoni sosial dan efisiensi logis."
                ),
                advice = listOf(
                    "INTJ: Belajar mengekspresikan rasa terima kasih secara terbuka.",
                    "ESFJ: Jangan menafsirkan keheningan sebagai penolakan.",
                    "Bangun komunikasi dua arah antara rasa dan logika."
                )
            ),

            RelationshipDetail("ISTP",
                strengths = listOf(
                    "Keduanya tenang, mandiri, dan menghargai efisiensi.",
                    "Sama-sama fokus pada keahlian dan penguasaan bidang tertentu.",
                    "Keseimbangan antara ide besar dan tindakan nyata."
                ),
                challenges = listOf(
                    "INTJ bisa menilai ISTP terlalu impulsif.",
                    "ISTP bisa menilai INTJ terlalu teoritis dan kaku.",
                    "Kurang komunikasi emosional bisa menciptakan jarak."
                ),
                advice = listOf(
                    "INTJ: Biarkan ruang bagi spontanitas dan aksi cepat ISTP.",
                    "ISTP: Hargai rencana jangka panjang INTJ meski tampak abstrak.",
                    "Temukan titik temu antara logika dan tindakan nyata."
                )
            ),

            RelationshipDetail("ISFP",
                strengths = listOf(
                    "ISFP membawa kehangatan lembut yang menenangkan INTJ.",
                    "INTJ memberi arah dan kestabilan pada kehidupan ISFP yang fleksibel.",
                    "Keduanya menghargai kedalaman, meski dalam bentuk berbeda."
                ),
                challenges = listOf(
                    "INTJ bisa tampak terlalu analitis bagi ISFP yang berperasaan.",
                    "ISFP bisa menilai INTJ terlalu jauh dari dunia nyata.",
                    "Perbedaan gaya hidup: terstruktur vs spontan."
                ),
                advice = listOf(
                    "INTJ: Belajar mengapresiasi keindahan momen tanpa rencana.",
                    "ISFP: Pahami bahwa keheningan INTJ sering kali bentuk perhatian.",
                    "Bangun keintiman lewat tindakan nyata, bukan kata-kata besar."
                )
            ),

            RelationshipDetail("ESTP",
                strengths = listOf(
                    "ESTP membawa energi dan keberanian yang menantang INTJ.",
                    "INTJ memberi arah strategis bagi keberanian spontan ESTP.",
                    "Kombinasi yang bisa produktif jika saling menghargai gaya masing-masing."
                ),
                challenges = listOf(
                    "ESTP bisa menilai INTJ terlalu lamban atau terlalu serius.",
                    "INTJ bisa kesulitan mengikuti ritme cepat ESTP.",
                    "Keduanya bisa saling mendominasi bila tidak seimbang."
                ),
                advice = listOf(
                    "INTJ: Sesekali ikuti arus tanpa rencana penuh.",
                    "ESTP: Jangan anggap perencanaan INTJ sebagai hambatan.",
                    "Gunakan perbedaan energi sebagai cara untuk saling melengkapi."
                )
            ),

            RelationshipDetail("ESFP",
                strengths = listOf(
                    "ESFP membawa cahaya dan spontanitas dalam hidup INTJ.",
                    "INTJ menawarkan kestabilan dan arah jangka panjang.",
                    "Keduanya bisa saling melengkapi antara logika dan emosi."
                ),
                challenges = listOf(
                    "ESFP bisa merasa INTJ terlalu kaku atau dingin.",
                    "INTJ bisa menilai ESFP terlalu berisik atau tidak fokus.",
                    "Perbedaan besar dalam cara menikmati hidup."
                ),
                advice = listOf(
                    "INTJ: Izinkan diri menikmati momen tanpa rencana.",
                    "ESFP: Jangan menafsirkan keheningan INTJ sebagai ketidaktertarikan.",
                    "Ciptakan keseimbangan antara spontanitas dan arah hidup."
                )
            )
        )
    ),
    "INTP" to PersonalityDetails(
        typeName = "INTP - Sang Ahli Logika",
        description = "Ia seorang pemikir alami yang hidup di antara arus ide dan teori. Dunia baginya adalah teka-teki besar yang menantang untuk diurai, bukan sekadar dijalani.",
        strengths = listOf("Sangat analitis", "Kreatif dalam berteori", "Mandiri secara intelektual", "Sangat ingin tahu"),
        weaknesses = listOf("Sulit bertindak", "Kurang peka emosional", "Mudah bosan pada hal praktis", "Terlalu kritis"),
        careerPaths = listOf("Peneliti akademik", "Programmer/Data scientist", "Analis sistem", "Filsuf"),
        relationships = "Ia membangun hubungan lewat keterikatan intelektual. Percakapan bermakna membuatnya merasa terhubung. Ia menghargai kebebasan berpikir dan tidak suka tekanan emosional.",
        developmentTips = listOf("Selesaikan satu proyek praktis sampai tuntas.", "Latih empati dengan mendengarkan tanpa menganalisis.", "Berbicara sederhana tentang perasaanmu."),
        detailImages = listOf(R.drawable.intpmain, R.drawable.intpdesc1, R.drawable.intpdesc2),
        viewsFromOthers = listOf(
            OtherTypeView("INTJ", "Menghargai orisinalitas pemikiran mereka, meski kadang kurang fokus pada eksekusi."),
            OtherTypeView("ENTJ", "Menghargai kemampuan mereka dalam menganalisa, tetapi berharap mereka lebih terorganisir dan fokus pada tujuan."),
            OtherTypeView("ENTP", "Merasa mereka adalah 'saudara intelektual', suka berdebat ide tanpa akhir."),
            OtherTypeView("INFJ", "Tertarik dengan kedalaman logikanya, tetapi merasa mereka kurang terhubung dengan sisi manusiawi."),
            OtherTypeView("INFP", "Menganggap mereka sangat cerdas, tapi sulit mengikuti alur pemikiran mereka yang bercabang."),
            OtherTypeView("ENFJ", "Melihat potensi brilian dalam diri mereka, berharap bisa membantu mereka lebih bersosialisasi."),
            OtherTypeView("ENFP", "Menyukai ide-ide gilanya, tapi berharap mereka bisa lebih peka pada perasaan."),
            OtherTypeView("ISTJ", "Menganggap mereka terlalu teoritis dan tidak praktis."),
            OtherTypeView("ISFJ", "Bingung dengan cara berpikir mereka yang abstrak, tapi mengagumi orisinalitas ide-idenya."),
            OtherTypeView("ESTJ", "Frustrasi dengan kurangnya tindakan nyata, tapi mengakui kecemerlangan analisis mereka."),
            OtherTypeView("ESFJ", "Merasa mereka terlalu pendiam dan sulit ditebak."),
            OtherTypeView("ISTP", "Melihatnya sebagai versi teoritis dari diri sendiri, menghormati logika mereka."),
            OtherTypeView("ISFP", "Sulit memahami dunia mereka yang penuh logika, tapi mengagumi imajinasinya."),
            OtherTypeView("ESTP", "Berpikir mereka terlalu banyak di dalam kepala, tapi mengakui mereka pintar."),
            OtherTypeView("ESFP", "Menganggap mereka menarik, tapi terlalu serius dan kurang spontan.")
        ),
        relationshipDetails = listOf(
            RelationshipDetail("INTJ",
                strengths = listOf(
                    "Saling menghargai kedalaman analisis dan logika.",
                    "Diskusi yang kaya akan teori dan konsep masa depan.",
                    "INTJ membawa arah strategis, INTP membawa ide-ide baru."
                ),
                challenges = listOf(
                    "INTP bisa merasa dikekang oleh struktur INTJ.",
                    "INTJ mungkin frustrasi pada ketidakteraturan INTP.",
                    "Kurangnya komunikasi emosional dapat menimbulkan jarak."
                ),
                advice = listOf(
                    "INTP: Terima struktur INTJ sebagai alat, bukan batas.",
                    "INTJ: Beri ruang bagi kebebasan berpikir INTP.",
                    "Gunakan kekuatan logika untuk membangun sinergi, bukan hierarki."
                )
            ),

            RelationshipDetail("ENTP",
                strengths = listOf(
                    "Keduanya suka bermain dengan ide dan kemungkinan.",
                    "Percakapan yang hidup, spontan, dan menantang pikiran.",
                    "Saling menstimulasi kreativitas intelektual."
                ),
                challenges = listOf(
                    "Kurangnya arah bisa membuat hubungan tidak stabil.",
                    "ENTP bisa terlalu cepat berubah, INTP bisa terlalu lambat bertindak.",
                    "Keduanya cenderung menunda realisasi ide."
                ),
                advice = listOf(
                    "INTP: Dorong diri untuk ikut bergerak, bukan hanya menganalisis.",
                    "ENTP: Beri ruang bagi INTP untuk berpikir tanpa interupsi.",
                    "Tetapkan proyek kecil yang menyalurkan ide kalian bersama."
                )
            ),

            RelationshipDetail("ENTJ",
                strengths = listOf(
                    "ENTJ memberi dorongan nyata bagi ide INTP.",
                    "Kombinasi logika dan visi yang bisa menghasilkan inovasi besar.",
                    "Keduanya menghargai kompetensi dan kecerdasan."
                ),
                challenges = listOf(
                    "ENTJ bisa terlalu menekan INTP untuk bertindak cepat.",
                    "INTP bisa tampak tidak fokus bagi ENTJ yang tegas.",
                    "Kurangnya komunikasi emosional bisa menimbulkan ketegangan."
                ),
                advice = listOf(
                    "INTP: Sampaikan gagasanmu secara jelas agar ENTJ bisa memahami nilainya.",
                    "ENTJ: Belajar menghargai proses berpikir lambat tapi mendalam INTP.",
                    "Gunakan visi ENTJ dan ide INTP untuk membangun sesuatu yang konkret."
                )
            ),

            RelationshipDetail("INFJ",
                strengths = listOf(
                    "Keduanya introspektif dan menghargai kedalaman makna.",
                    "INFJ membawa empati, INTP membawa objektivitas.",
                    "Diskusi dapat membuka wawasan emosional dan filosofis baru."
                ),
                challenges = listOf(
                    "INTP bisa terlalu analitis bagi INFJ yang emosional.",
                    "INFJ mungkin merasa kurang didengar secara perasaan.",
                    "Keduanya bisa menutup diri saat konflik muncul."
                ),
                advice = listOf(
                    "INTP: Cobalah memahami makna di balik emosi INFJ.",
                    "INFJ: Jangan menganggap jarak INTP sebagai ketidakpedulian.",
                    "Bangun jembatan antara logika dan empati melalui kejujuran tenang."
                )
            ),

            RelationshipDetail("INFP",
                strengths = listOf(
                    "Keduanya imajinatif dan menghargai keaslian diri.",
                    "INFP membawa kehangatan dan nilai, INTP membawa logika yang menyeimbangkan.",
                    "Saling menginspirasi untuk berpikir lebih luas dan mendalam."
                ),
                challenges = listOf(
                    "INTP bisa tampak dingin bagi INFP yang peka.",
                    "INFP bisa tampak terlalu emosional bagi INTP yang rasional.",
                    "Perbedaan dalam mengambil keputusan: nilai vs logika."
                ),
                advice = listOf(
                    "INTP: Dengarkan perasaan INFP tanpa menganalisis berlebihan.",
                    "INFP: Hargai cara berpikir INTP yang netral dan ingin memahami.",
                    "Bersama, temukan keseimbangan antara hati dan pikiran."
                )
            ),

            RelationshipDetail("ENFP",
                strengths = listOf(
                    "Keduanya haus akan ide dan eksplorasi konsep baru.",
                    "ENFP membawa energi dan antusiasme yang menghidupkan INTP.",
                    "INTP memberi struktur lembut pada imajinasi ENFP."
                ),
                challenges = listOf(
                    "INTP bisa kewalahan oleh emosi dan spontanitas ENFP.",
                    "ENFP bisa frustrasi pada ketidakpastian dan jarak INTP.",
                    "Keduanya bisa tersesat dalam kemungkinan tanpa hasil nyata."
                ),
                advice = listOf(
                    "INTP: Tanggapi energi ENFP sebagai inspirasi, bukan gangguan.",
                    "ENFP: Beri waktu bagi INTP untuk merenung sebelum menanggapi.",
                    "Gunakan kombinasi ide dan semangat untuk mencipta, bukan sekadar berimajinasi."
                )
            ),

            RelationshipDetail("ENFJ",
                strengths = listOf(
                    "ENFJ membawa empati dan arah sosial, INTP membawa analisis jernih.",
                    "Saling mengajarkan cara menyeimbangkan logika dan hati.",
                    "Hubungan dapat berkembang dalam suasana saling belajar dan menghormati."
                ),
                challenges = listOf(
                    "ENFJ bisa merasa INTP terlalu dingin atau pasif.",
                    "INTP bisa merasa ENFJ terlalu intens dan menuntut koneksi emosional.",
                    "Perbedaan bahasa antara rasio dan perasaan."
                ),
                advice = listOf(
                    "INTP: Belajar mengekspresikan niat baik secara lebih terbuka.",
                    "ENFJ: Hargai ruang dan diam yang dibutuhkan INTP.",
                    "Gunakan perbedaan kalian untuk membangun harmoni antara refleksi dan aksi."
                )
            ),

            RelationshipDetail("ISTJ",
                strengths = listOf(
                    "Keduanya menghargai logika dan akurasi.",
                    "ISTJ membawa ketertiban, INTP membawa perspektif baru.",
                    "Kombinasi yang solid antara ketelitian dan kreativitas rasional."
                ),
                challenges = listOf(
                    "ISTJ bisa frustrasi pada ketidakteraturan INTP.",
                    "INTP bisa merasa terkekang oleh aturan ISTJ.",
                    "Kurangnya komunikasi emosional dapat menciptakan kesalahpahaman."
                ),
                advice = listOf(
                    "INTP: Hargai struktur ISTJ sebagai penopang, bukan penjara.",
                    "ISTJ: Terima ide INTP meski tampak eksentrik.",
                    "Gunakan logika bersama untuk menemukan titik keseimbangan baru."
                )
            ),

            RelationshipDetail("ISFJ",
                strengths = listOf(
                    "ISFJ memberi kestabilan dan kehangatan, INTP memberi pandangan segar.",
                    "Keduanya cenderung tenang dan introspektif.",
                    "Saling melengkapi antara kepedulian dan logika."
                ),
                challenges = listOf(
                    "ISFJ bisa merasa diabaikan oleh INTP yang terlalu fokus berpikir.",
                    "INTP bisa merasa kewalahan oleh kebutuhan emosional ISFJ.",
                    "Perbedaan dalam gaya hidup: teratur vs fleksibel."
                ),
                advice = listOf(
                    "INTP: Tunjukkan penghargaan pada perhatian kecil dari ISFJ.",
                    "ISFJ: Jangan menafsirkan jarak INTP sebagai ketidaksukaan.",
                    "Bangun keseimbangan antara rasa aman dan kebebasan berpikir."
                )
            ),

            RelationshipDetail("ESTJ",
                strengths = listOf(
                    "ESTJ membawa struktur dan efisiensi, INTP membawa inovasi logis.",
                    "Keduanya menghargai rasionalitas dan kompetensi.",
                    "Bisa menjadi tim yang kuat bila saling menghormati peran."
                ),
                challenges = listOf(
                    "ESTJ bisa menilai INTP terlalu pasif atau tidak praktis.",
                    "INTP bisa menilai ESTJ terlalu keras kepala dan dominan.",
                    "Perbedaan ritme dalam bertindak dan mengambil keputusan."
                ),
                advice = listOf(
                    "INTP: Belajar menjelaskan ide dalam kerangka praktis.",
                    "ESTJ: Beri waktu bagi INTP untuk berpikir sebelum menindak.",
                    "Gunakan logika bersama untuk menciptakan hasil nyata tanpa menekan."
                )
            ),

            RelationshipDetail("ESFJ",
                strengths = listOf(
                    "ESFJ membawa perhatian sosial, INTP membawa wawasan rasional.",
                    "Keduanya dapat saling menyeimbangkan antara perasaan dan pikiran.",
                    "Sama-sama menghargai kejujuran dan niat baik."
                ),
                challenges = listOf(
                    "ESFJ bisa merasa INTP tidak cukup hangat atau responsif.",
                    "INTP bisa kewalahan oleh intensitas sosial ESFJ.",
                    "Perbedaan fokus antara hubungan manusia dan ide."
                ),
                advice = listOf(
                    "INTP: Belajar memberi tanda perhatian sederhana.",
                    "ESFJ: Jangan menganggap keheningan INTP sebagai penolakan.",
                    "Gunakan logika dan empati untuk saling memahami lebih dalam."
                )
            ),

            RelationshipDetail("ISTP",
                strengths = listOf(
                    "Keduanya analitis, tenang, dan menghargai otonomi.",
                    "Saling memahami kebutuhan ruang pribadi dan kebebasan berpikir.",
                    "Gabungan yang kuat antara ide dan tindakan."
                ),
                challenges = listOf(
                    "INTP bisa terlalu teoritis bagi ISTP yang praktis.",
                    "ISTP bisa menilai INTP lamban dalam mengambil keputusan.",
                    "Keduanya cenderung menghindari pembicaraan emosional."
                ),
                advice = listOf(
                    "INTP: Hargai aksi nyata ISTP sebagai bentuk kecerdasan praktis.",
                    "ISTP: Jangan meremehkan kedalaman ide INTP.",
                    "Temukan proyek nyata untuk menyalurkan logika kalian bersama."
                )
            ),

            RelationshipDetail("ISFP",
                strengths = listOf(
                    "ISFP membawa kehangatan lembut, INTP membawa logika yang tenang.",
                    "Saling belajar tentang dunia batin dan cara mengekspresikannya.",
                    "Keduanya menghargai kebebasan individu."
                ),
                challenges = listOf(
                    "INTP bisa tampak dingin bagi ISFP yang emosional.",
                    "ISFP bisa tampak tidak terstruktur bagi INTP yang analitis.",
                    "Kurangnya komunikasi langsung bisa menimbulkan jarak."
                ),
                advice = listOf(
                    "INTP: Cobalah menunjukkan perhatian melalui tindakan kecil.",
                    "ISFP: Jangan takut memberi tahu kebutuhan emosional secara jelas.",
                    "Bangun koneksi lewat kesederhanaan dan saling menghormati ruang pribadi."
                )
            ),

            RelationshipDetail("ESTP",
                strengths = listOf(
                    "ESTP membawa energi dan pengalaman langsung.",
                    "INTP memberi kedalaman reflektif terhadap tindakan ESTP.",
                    "Saling menyeimbangkan antara spontanitas dan pemikiran logis."
                ),
                challenges = listOf(
                    "ESTP bisa merasa INTP terlalu pasif.",
                    "INTP bisa menilai ESTP terlalu impulsif.",
                    "Kesulitan sinkron antara logika abstrak dan tindakan cepat."
                ),
                advice = listOf(
                    "INTP: Sesekali nikmati aksi tanpa terlalu menganalisis.",
                    "ESTP: Dengarkan pandangan INTP sebelum bertindak.",
                    "Belajar menggabungkan strategi dengan momentum nyata."
                )
            ),

            RelationshipDetail("ESFP",
                strengths = listOf(
                    "ESFP membawa keceriaan dan pengalaman langsung pada dunia INTP.",
                    "INTP membantu ESFP merefleksikan makna di balik pengalaman.",
                    "Keduanya bisa belajar banyak tentang hidup dari sudut yang berbeda."
                ),
                challenges = listOf(
                    "ESFP bisa frustrasi pada jarak emosional INTP.",
                    "INTP bisa kewalahan dengan intensitas dan spontanitas ESFP.",
                    "Perbedaan cara memahami kebahagiaan: momen vs makna."
                ),
                advice = listOf(
                    "INTP: Nikmati energi hidup ESFP tanpa mengkritik terlalu cepat.",
                    "ESFP: Hormati ruang reflektif INTP tanpa merasa diabaikan.",
                    "Temukan titik temu antara bermain dan berpikir."
                )
            )
        )
    ),
    "ENTJ" to PersonalityDetails(
        typeName = "ENTJ - Sang Komandan",
        description = "Ia seorang pemimpin yang melihat jalur maju di tengah kebingungan. Ketegasan dan rasa tanggung jawabnya membuatnya sigap mengambil kendali ketika arah perlu ditetapkan.",
        strengths = listOf("Pemimpin tegas", "Ahli strategi", "Efisien", "Percaya diri"),
        weaknesses = listOf("Mendominasi", "Kurang peka perasaan", "Sulit menerima kritik", "Sangat menuntut"),
        careerPaths = listOf("CEO", "Manajer proyek", "Konsultan manajemen", "Pengacara"),
        relationships = "Ia menunjukkan cinta lewat komitmen dan aksi yang konkret: menetapkan arah, membantu pasangan mencapai tujuan, dan melindungi kestabilan. Ia menghargai keterbukaan dan kompetensi.",
        developmentTips = listOf("Latih mendengar tanpa memberi solusi.", "Sisihkan waktu untuk hubungan tanpa agenda.", "Berikan pujian secara eksplisit."),
        detailImages = listOf(R.drawable.entjmain, R.drawable.entjdesc1, R.drawable.entjdesc2),
        viewsFromOthers = listOf(
            OtherTypeView("INTJ", "Menghargai visi dan ketegasan mereka, tapi kadang merasa mereka kurang fleksibel."),
            OtherTypeView("INTP", "Menghargai ketegasan dan kemampuan mereka dalam memimpin, meskipun kadang merasa terlalu dikendalikan."),
            OtherTypeView("ENTP", "Melihatnya sebagai eksekutor yang hebat untuk ide-ide liar, meski kadang terlalu kaku dengan rencana."),
            OtherTypeView("INFJ", "Mengagumi kepemimpinan mereka, tapi berharap mereka lebih mempertimbangkan dampak emosional."),
            OtherTypeView("INFP", "Merasa terintimidasi oleh energi mereka yang kuat, tapi mengakui kehebatan visi mereka."),
            OtherTypeView("ENFJ", "Melihatnya sebagai pemimpin yang kuat, tapi dengan gaya yang sangat berbeda."),
            OtherTypeView("ENFP", "Menghargai kemampuan mereka mewujudkan ide, tapi merasa terkekang oleh strukturnya."),
            OtherTypeView("ISTJ", "Menghormati etos kerja dan organisasinya, sering menjadi 'tangan kanan' yang andal."),
            OtherTypeView("ISFJ", "Menganggap mereka pemimpin yang kompeten, meskipun kadang menakutkan."),
            OtherTypeView("ESTJ", "Melihatnya sebagai rekan pemimpin alami, sering bersaing atau berkolaborasi dengan baik."),
            OtherTypeView("ESFJ", "Menghargai kemampuan mereka mengorganisir, tapi berharap mereka lebih hangat."),
            OtherTypeView("ISTP", "Frustrasi dengan aturan mereka yang ketat, tapi mengakui mereka bisa mencapai hasil."),
            OtherTypeView("ISFP", "Merasa tertekan oleh sifat dominan mereka, tetapi mengakui efektivitas mereka dalam mencapai hasil."),
            OtherTypeView("ESTP", "Melihatnya sebagai bos yang tangguh, suka menantang otoritas mereka."),
            OtherTypeView("ESFP", "Menganggap mereka terlalu fokus pada pekerjaan dan kurang bisa bersenang-senang.")
        ),
        relationshipDetails = listOf(
            RelationshipDetail("INTJ",
                strengths = listOf(
                    "Keduanya visioner, fokus pada strategi dan efisiensi.",
                    "INTJ memberikan kedalaman analisis, ENTJ memberikan arah tindakan.",
                    "Saling menghormati kompetensi dan ketegasan berpikir."
                ),
                challenges = listOf(
                    "Benturan ego dapat terjadi karena keduanya kuat dan mandiri.",
                    "ENTJ bisa menganggap INTJ terlalu lamban, INTJ menganggap ENTJ terlalu mendesak.",
                    "Kurang ekspresi emosional bisa menciptakan jarak."
                ),
                advice = listOf(
                    "ENTJ: Beri ruang bagi INTJ untuk berpikir dan menyusun strategi.",
                    "INTJ: Hargai kebutuhan ENTJ untuk melihat hasil konkret.",
                    "Gunakan visi bersama untuk membangun sistem yang seimbang antara refleksi dan aksi."
                )
            ),
            RelationshipDetail("INTP",
                strengths = listOf(
                    "INTP memberikan ide-ide konseptual, ENTJ membawa mereka ke kenyataan.",
                    "Saling menghargai kecerdasan dan objektivitas.",
                    "Kolaborasi yang kuat antara teori dan implementasi."
                ),
                challenges = listOf(
                    "ENTJ bisa menilai INTP terlalu pasif atau bertele-tele.",
                    "INTP bisa merasa ditekan oleh ritme ENTJ yang cepat.",
                    "Keduanya bisa sulit memahami kebutuhan emosional satu sama lain."
                ),
                advice = listOf(
                    "ENTJ: Hargai kedalaman refleksi INTP meski tampak lambat.",
                    "INTP: Sampaikan ide secara konkret agar ENTJ dapat menindaklanjutinya.",
                    "Bangun komunikasi dua arah yang menyeimbangkan dorongan dan kedalaman."
                )
            ),
            RelationshipDetail("ENTP",
                strengths = listOf(
                    "Pertemuan dua pemikir dinamis yang penuh ide dan ambisi.",
                    "ENTP membawa kreativitas liar, ENTJ memberi arah dan fokus.",
                    "Energi tinggi yang bisa menghasilkan inovasi besar."
                ),
                challenges = listOf(
                    "ENTP bisa frustrasi dengan kontrol ENTJ.",
                    "ENTJ bisa kesal dengan kurangnya konsistensi ENTP.",
                    "Keduanya bisa terjebak dalam debat tanpa kesepakatan."
                ),
                advice = listOf(
                    "ENTJ: Biarkan ruang bagi eksplorasi ide tanpa tuntutan segera.",
                    "ENTP: Tunjukkan komitmen nyata agar ENTJ merasa stabil.",
                    "Gunakan perdebatan sebagai alat membangun, bukan mengalahkan."
                )
            ),
            RelationshipDetail("INFJ",
                strengths = listOf(
                    "INFJ membawa empati dan intuisi manusiawi, ENTJ membawa arah dan ketegasan.",
                    "Keduanya memiliki visi jangka panjang dan dorongan untuk tumbuh.",
                    "Dapat saling melengkapi antara hati dan strategi."
                ),
                challenges = listOf(
                    "ENTJ bisa tampak terlalu keras bagi INFJ yang sensitif.",
                    "INFJ bisa menilai ENTJ kurang peka terhadap emosi.",
                    "Perbedaan fokus: hasil konkret vs makna batin."
                ),
                advice = listOf(
                    "ENTJ: Luangkan waktu untuk mendengarkan tanpa menilai efisiensinya.",
                    "INFJ: Hargai niat baik di balik ketegasan ENTJ.",
                    "Gunakan visi bersama untuk menyeimbangkan kekuatan dan kelembutan."
                )
            ),
            RelationshipDetail("INFP",
                strengths = listOf(
                    "INFP membawa nilai dan idealisme, ENTJ membawa struktur dan arah.",
                    "Saling menginspirasi: hati INFP memberi makna pada ambisi ENTJ.",
                    "Keduanya bisa tumbuh melalui saling memahami perbedaan dunia batin."
                ),
                challenges = listOf(
                    "ENTJ bisa menilai INFP terlalu sensitif dan lambat.",
                    "INFP bisa merasa ditekan oleh dominasi ENTJ.",
                    "Perbedaan besar dalam cara memandang kesuksesan dan harmoni."
                ),
                advice = listOf(
                    "ENTJ: Gunakan empati saat memberi kritik atau arahan.",
                    "INFP: Lihat ketegasan ENTJ sebagai bentuk perhatian, bukan kontrol.",
                    "Temukan makna bersama antara pencapaian dan kesejahteraan batin."
                )
            ),
            RelationshipDetail("ENFP",
                strengths = listOf(
                    "Keduanya energik, penuh ide, dan menularkan semangat pada dunia.",
                    "ENFP membawa kreativitas emosional, ENTJ membawa arah konkret.",
                    "Kombinasi ideal antara inspirasi dan eksekusi."
                ),
                challenges = listOf(
                    "ENTJ bisa menilai ENFP tidak fokus, ENFP menilai ENTJ terlalu kaku.",
                    "Konflik bisa muncul karena perbedaan cara mengekspresikan emosi.",
                    "Keduanya bisa merasa kelebihan arah atau kelebihan emosi."
                ),
                advice = listOf(
                    "ENTJ: Biarkan ENFP memunculkan ide tanpa langsung dikritik.",
                    "ENFP: Hargai ketegasan ENTJ sebagai bentuk kepedulian.",
                    "Bangun ritme bersama antara imajinasi dan implementasi."
                )
            ),
            RelationshipDetail("ENFJ",
                strengths = listOf(
                    "Dua pemimpin karismatik yang bisa saling memperkuat.",
                    "ENTJ membawa strategi, ENFJ membawa koneksi manusiawi.",
                    "Keduanya berorientasi pada pertumbuhan dan pencapaian bersama."
                ),
                challenges = listOf(
                    "Benturan bisa terjadi karena keduanya dominan dan idealistik.",
                    "ENTJ lebih berfokus pada sistem, ENFJ pada hubungan.",
                    "Keduanya bisa lelah bila tidak belajar bergantian memimpin."
                ),
                advice = listOf(
                    "ENTJ: Tunjukkan penghargaan terhadap sensitivitas ENFJ.",
                    "ENFJ: Jangan takut berbicara tegas kepada ENTJ.",
                    "Gunakan kekuatan kepemimpinan untuk membangun, bukan mendominasi."
                )
            ),
            RelationshipDetail("ISTJ",
                strengths = listOf(
                    "Sama-sama logis, disiplin, dan berorientasi hasil.",
                    "ISTJ membawa stabilitas, ENTJ membawa arah strategis.",
                    "Kerja sama efektif bila nilai tanggung jawab selaras."
                ),
                challenges = listOf(
                    "ISTJ bisa menilai ENTJ terlalu cepat dan ambisius.",
                    "ENTJ bisa merasa ISTJ terlalu berhati-hati.",
                    "Perbedaan pendekatan terhadap perubahan."
                ),
                advice = listOf(
                    "ENTJ: Hargai ketelitian ISTJ sebagai bentuk dedikasi.",
                    "ISTJ: Terbuka pada ide baru yang dibawa ENTJ.",
                    "Bangun sistem yang menggabungkan kecepatan dan ketepatan."
                )
            ),
            RelationshipDetail("ISFJ",
                strengths = listOf(
                    "ISFJ membawa ketenangan dan kepedulian, ENTJ membawa arah dan dorongan.",
                    "Saling melengkapi dalam hal visi dan pelaksanaan detail.",
                    "Keduanya bisa membangun lingkungan yang aman dan produktif."
                ),
                challenges = listOf(
                    "ENTJ bisa tampak menuntut bagi ISFJ yang sensitif.",
                    "ISFJ bisa merasa kewalahan oleh energi dan ketegasan ENTJ.",
                    "Perbedaan prioritas antara harmoni dan hasil."
                ),
                advice = listOf(
                    "ENTJ: Belajar memperlambat ritme dan mendengarkan perasaan ISFJ.",
                    "ISFJ: Jangan takut memberi masukan pada ENTJ secara jujur.",
                    "Gunakan kekuatan stabilitas dan visi untuk menciptakan keseimbangan kerja dan hati."
                )
            ),
            RelationshipDetail("ESTJ",
                strengths = listOf(
                    "Dua tipe eksekutif yang efisien dan berorientasi prestasi.",
                    "Saling memahami nilai tanggung jawab dan ketegasan.",
                    "Dapat membentuk tim kepemimpinan yang sangat efektif."
                ),
                challenges = listOf(
                    "Benturan ego bisa terjadi karena keduanya dominan.",
                    "Kurangnya fleksibilitas dapat memicu konflik arah.",
                    "Risiko terlalu fokus pada hasil dan mengabaikan keseimbangan personal."
                ),
                advice = listOf(
                    "ENTJ: Belajar memberi ruang bagi pendekatan ESTJ yang lebih praktis.",
                    "ESTJ: Hargai pandangan jangka panjang ENTJ meski tampak ambisius.",
                    "Fokus pada tujuan bersama daripada siapa yang memimpin."
                )
            ),
            RelationshipDetail("ESFJ",
                strengths = listOf(
                    "ESFJ membawa kehangatan dan harmoni sosial, ENTJ membawa struktur dan visi.",
                    "Saling menyeimbangkan antara manusia dan sistem.",
                    "Keduanya bisa membangun lingkungan produktif dan suportif."
                ),
                challenges = listOf(
                    "ENTJ bisa tampak dingin bagi ESFJ yang emosional.",
                    "ESFJ bisa merasa tidak dihargai bila ENTJ terlalu fokus pada hasil.",
                    "Perbedaan dalam cara menunjukkan kepedulian."
                ),
                advice = listOf(
                    "ENTJ: Hargai perhatian ESFJ sebagai kekuatan, bukan kelemahan.",
                    "ESFJ: Pahami bahwa gaya tegas ENTJ sering dilandasi niat baik.",
                    "Bangun komunikasi yang menjaga keseimbangan antara rasa dan logika."
                )
            ),
            RelationshipDetail("ISTP",
                strengths = listOf(
                    "Keduanya logis, mandiri, dan fokus pada efisiensi.",
                    "ISTP membawa ketenangan, ENTJ membawa dorongan aksi.",
                    "Bisa membentuk tim yang tangguh dan solutif."
                ),
                challenges = listOf(
                    "ENTJ bisa menilai ISTP terlalu pasif.",
                    "ISTP bisa merasa ditekan oleh dorongan ENTJ yang intens.",
                    "Kurangnya diskusi emosional dapat membuat hubungan terasa datar."
                ),
                advice = listOf(
                    "ENTJ: Biarkan ISTP bekerja dengan ritmenya sendiri.",
                    "ISTP: Beri umpan balik agar ENTJ tahu arah komunikasi yang efektif.",
                    "Fokus pada hasil nyata tanpa menekan proses alami masing-masing."
                )
            ),
            RelationshipDetail("ISFP",
                strengths = listOf(
                    "ISFP membawa kelembutan dan keindahan pada dunia ENTJ yang logis.",
                    "ENTJ memberi struktur dan perlindungan bagi ISFP yang lembut.",
                    "Saling melengkapi antara ketegasan dan kepekaan."
                ),
                challenges = listOf(
                    "ENTJ bisa tampak terlalu menuntut bagi ISFP.",
                    "ISFP bisa tampak tidak terarah bagi ENTJ.",
                    "Kesenjangan emosional dapat muncul bila tidak dijembatani."
                ),
                advice = listOf(
                    "ENTJ: Tunjukkan penghargaan atas sisi artistik dan keheningan ISFP.",
                    "ISFP: Pahami bahwa kontrol ENTJ sering lahir dari rasa tanggung jawab.",
                    "Gunakan perbedaan ini untuk membangun rasa aman dan saling percaya."
                )
            ),
            RelationshipDetail("ESTP",
                strengths = listOf(
                    "Dua pribadi energik dan berani yang menyukai tantangan.",
                    "ESTP membawa spontanitas, ENTJ membawa arah strategis.",
                    "Keduanya bisa memotivasi satu sama lain untuk sukses."
                ),
                challenges = listOf(
                    "ENTJ bisa menilai ESTP terlalu impulsif.",
                    "ESTP bisa menilai ENTJ terlalu serius atau mengontrol.",
                    "Risiko konflik karena keduanya menyukai kendali."
                ),
                advice = listOf(
                    "ENTJ: Beri ruang bagi spontanitas tanpa kehilangan arah.",
                    "ESTP: Hargai visi jangka panjang ENTJ.",
                    "Gunakan energi dan logika untuk menciptakan petualangan yang bermakna."
                )
            ),
            RelationshipDetail("ESFP",
                strengths = listOf(
                    "ESFP membawa kegembiraan dan empati, ENTJ membawa tujuan dan fokus.",
                    "Keduanya bisa saling menginspirasi untuk hidup dengan semangat dan arah.",
                    "Saling menyeimbangkan antara kerja keras dan menikmati hidup."
                ),
                challenges = listOf(
                    "ENTJ bisa tampak dingin atau kaku bagi ESFP yang spontan.",
                    "ESFP bisa tampak tidak disiplin bagi ENTJ.",
                    "Perbedaan gaya hidup: hasil vs pengalaman."
                ),
                advice = listOf(
                    "ENTJ: Belajar menikmati momen tanpa menilai efisiensinya.",
                    "ESFP: Pahami bahwa ambisi ENTJ berasal dari rasa tanggung jawab.",
                    "Bangun ritme hidup yang memadukan kerja keras dan kegembiraan."
                )
            )
        )
    ),
    "ENTP" to PersonalityDetails(
        typeName = "ENTP - Sang Pendebat",
        description = "Ia seorang pencari kemungkinan yang senang memutar argumen demi menguji gagasan. Ia menyukai stimulasi intelektual dan tantangan mental.",
        strengths = listOf("Cepat berpikir", "Pandai menghubungkan gagasan", "Karismatik", "Berani menantang"),
        weaknesses = listOf("Sulit menyelesaikan proyek", "Mudah bosan", "Terlihat provokatif", "Tidak sabaran"),
        careerPaths = listOf("Pengusaha inovatif", "Konsultan kreatif", "Pengacara", "Jurnalis"),
        relationships = "Dalam relasi, ia menyukai pasangan yang dapat terlibat dalam percakapan tajam dan humor intelektual. Ia menunjukkan kasih lewat stimulasi mental dan ide bersama.",
        developmentTips = listOf("Selesaikan satu ide menjadi bentuk nyata.", "Perhatikan bahasa tubuh saat berdebat.", "Latih komitmen kecil."),
        detailImages = listOf(R.drawable.entpmain, R.drawable.entpdesc1, R.drawable.entpdesc2),
        viewsFromOthers = listOf(
            OtherTypeView("INTJ", "Menikmati diskusi intelektualnya, tapi berharap mereka lebih fokus pada satu kesimpulan."),
            OtherTypeView("INTP", "Merasa mereka adalah 'partner in crime' dalam eksplorasi ide, meski kadang terlalu suka pamer."),
            OtherTypeView("ENTJ", "Melihatnya sebagai sumber ide brilian, tapi perlu diikat agar tetap di jalur."),
            OtherTypeView("INFJ", "Tertarik dengan ide-ide mereka yang tak ada habisnya, namun lelah dengan kecenderungan mereka untuk berdebat."),
            OtherTypeView("INFP", "Menganggap ide-idenya menarik, tapi merasa diserang saat mereka mulai berdebat."),
            OtherTypeView("ENFJ", "Menyukai energi kreatif mereka, tapi berharap mereka lebih sensitif secara sosial."),
            OtherTypeView("ENFP", "Merasa seperti versi yang lebih logis dari diri sendiri, teman brainstorming yang hebat."),
            OtherTypeView("ISTJ", "Menganggap mereka tidak praktis dan tidak terorganisir, tetapi mengakui kreativitas mereka."),
            OtherTypeView("ISFJ", "Merasa cara mereka berdebat itu kasar, tapi kadang terhibur oleh humornya."),
            OtherTypeView("ESTJ", "Melihatnya sebagai pembuat onar yang cerdas, bisa jadi aset atau masalah."),
            OtherTypeView("ESFJ", "Khawatir mereka akan mempermalukan diri sendiri di depan umum, tapi mengakui pesonanya."),
            OtherTypeView("ISTP", "Suka beradu argumen teknis dengan mereka, tapi tidak suka debat filosofisnya."),
            OtherTypeView("ISFP", "Merasa energi mereka terlalu kacau dan tidak menenangkan."),
            OtherTypeView("ESTP", "Melihatnya sebagai rival yang menyenangkan dalam hal kecerdasan dan kecepatan berpikir."),
            OtherTypeView("ESFP", "Menganggap mereka lucu dan menghibur, selama perdebatan tidak menjadi terlalu serius.")
        ),
        relationshipDetails = listOf(
            RelationshipDetail("INTJ",
                strengths = listOf("Keduanya analitis dan suka membedah konsep mendalam.", "INTJ membawa fokus strategis, ENTP membawa perspektif baru yang segar.", "Diskusi mereka sering mengarah pada ide-ide visioner yang konkret."),
                challenges = listOf("ENTP bisa tampak tidak konsisten bagi INTJ.", "INTJ bisa menilai ENTP terlalu impulsif.", "Perbedaan dalam kebutuhan struktur vs eksplorasi bisa memicu gesekan."),
                advice = listOf("ENTP: Hargai kedisiplinan dan keheningan INTJ.", "INTJ: Biarkan ENTP menjelajah tanpa langsung menilai chaos-nya.", "Gunakan gabungan visi dan fleksibilitas untuk membangun sesuatu yang besar.")
            ),
            RelationshipDetail("INTP",
                strengths = listOf("Keduanya pemikir bebas yang haus pengetahuan.", "Saling mendorong eksplorasi ide secara mendalam.", "Percakapan terasa seperti laboratorium intelektual yang tak ada ujungnya."),
                challenges = listOf("Kurangnya arah konkret bisa membuat hubungan stagnan.", "ENTP bisa bosan bila INTP terlalu lama di teori.", "INTP bisa jenuh bila ENTP terlalu dominan secara sosial."),
                advice = listOf("ENTP: Belajar menahan dorongan debat demi mendengar kedalaman logika INTP.", "INTP: Sampaikan batasan agar diskusi tetap sehat.", "Tentukan tujuan bersama agar intelektualitas berbuah nyata.")
            ),
            RelationshipDetail("ENTJ",
                strengths = listOf("Keduanya energik, logis, dan sangat berorientasi ide besar.", "ENTJ menghargai kreativitas ENTP, ENTP menghargai ketegasan ENTJ.", "Potensi luar biasa dalam kerja sama strategis dan inovatif."),
                challenges = listOf("ENTP bisa merasa ditekan oleh kebutuhan struktur ENTJ.", "ENTJ bisa jengkel pada fleksibilitas ENTP yang tampak berantakan.", "Perselisihan bisa muncul bila keduanya ingin memimpin arah."),
                advice = listOf("ENTP: Hargai kejelasan visi ENTJ sebagai jangkar ide-ide liar.", "ENTJ: Biarkan sedikit kekacauan untuk memberi ruang kreativitas.", "Gunakan kombinasi imajinasi dan strategi untuk menggerakkan dunia.")
            ),
            RelationshipDetail("INFJ",
                strengths = listOf("INFJ membawa kedalaman jiwa, ENTP membawa pandangan luas dan segar.", "Keduanya sama-sama tertarik memahami pola manusia dan makna di baliknya.", "Bila terjalin kepercayaan, kombinasi ini bisa sangat mendalam dan menumbuhkan."),
                challenges = listOf("ENTP bisa tampak tidak peka terhadap sensitivitas INFJ.", "INFJ bisa menilai ENTP terlalu bermain-main dengan perasaan.", "Perbedaan besar antara spontanitas dan kehati-hatian."),
                advice = listOf("ENTP: Tahan godaan untuk mengubah arah pembicaraan hanya demi tantangan intelektual.", "INFJ: Lihat rasa ingin tahu ENTP sebagai bentuk perhatian, bukan gangguan.", "Temukan ritme antara visi batin dan petualangan luar.")
            ),
            RelationshipDetail("INFP",
                strengths = listOf("INFP memberi idealisme lembut, ENTP memberi semangat eksplorasi.", "Keduanya suka membicarakan makna dan kemungkinan hidup.", "Saling menumbuhkan sisi imajinatif dan empatik."),
                challenges = listOf("ENTP bisa tanpa sadar menertawakan hal yang INFP anggap sakral.", "INFP bisa menarik diri bila merasa diabaikan secara emosional.", "Perbedaan gaya komunikasi bisa menciptakan salah paham."),
                advice = listOf("ENTP: Perlambat ritme saat membahas hal yang penting bagi INFP.", "INFP: Jangan takut menyuarakan kebutuhan emosional secara langsung.", "Gunakan imajinasi bersama untuk menyulam makna dan petualangan.")
            ),
            RelationshipDetail("ENFP",
                strengths = listOf("Dua jiwa bebas penuh tawa, ide, dan rasa ingin tahu tak terbatas.", "Kreativitas mereka menular dan menyegarkan lingkungan.", "Hubungan ini penuh spontanitas dan pemikiran out-of-the-box."),
                challenges = listOf("Keduanya bisa terlalu sibuk berimajinasi tanpa realisasi.", "Rentan pada ketidakkonsistenan dan perubahan arah mendadak.", "Bisa saling bersaing dalam hal perhatian dan karisma."),
                advice = listOf("ENTP: Jaga agar percakapan tetap memiliki arah dan makna.", "ENFP: Biarkan logika ENTP menyeimbangkan emosimu tanpa menguranginya.", "Bangun sistem sederhana agar mimpi bisa hidup di dunia nyata.")
            ),
            RelationshipDetail("ENFJ",
                strengths = listOf("ENFJ membawa kehangatan dan struktur sosial, ENTP membawa ide dan dinamika.", "Saling melengkapi antara visi emosional dan logika kreatif.", "Keduanya komunikatif, energik, dan saling menstimulasi."),
                challenges = listOf("ENTP bisa tampak tidak serius bagi ENFJ yang terencana.", "ENFJ bisa menilai ENTP terlalu egois atau tidak konsisten.", "Risiko salah paham dalam membaca niat satu sama lain."),
                advice = listOf("ENTP: Tunjukkan komitmen kecil tapi nyata untuk membangun kepercayaan.", "ENFJ: Biarkan ENTP berimprovisasi tanpa langsung dikoreksi.", "Gunakan empati dan humor untuk menjaga keseimbangan dialog.")
            )
        )
    ),
    "INFJ" to PersonalityDetails(
        typeName = "INFJ - Sang Advokat",
        description = "Ia seorang yang menatap jauh melampaui permukaan. Dalam dirinya mengalir hasrat untuk memahami makna di balik setiap peristiwa, serta kerinduan untuk membawa kebaikan yang sunyi namun nyata.",
        strengths = listOf("Empati mendalam", "Visi jangka panjang", "Setia & berkomitmen", "Membimbing dengan tenang"),
        weaknesses = listOf("Mudah terbebani emosi", "Menyembunyikan beban", "Perfeksionis", "Enggan berkonfrontasi"),
        careerPaths = listOf("Konselor/Terapis", "Penulis", "Pekerja nirlaba", "Psikolog"),
        relationships = "Ia mencari hubungan yang mendalam dan bermakna. Cinta baginya adalah ruang untuk berbagi nilai, mimpi, dan ketakutan terdalam. Ia butuh pasangan yang sabar dan konsisten.",
        developmentTips = listOf("Izinkan dirimu berbagi beban.", "Terima bahwa dunia tidak ideal.", "Jadwalkan waktu untuk pulih."),
        detailImages = listOf(R.drawable.infjmain, R.drawable.infjdesc1, R.drawable.infjdesc2),
        viewsFromOthers = listOf(
            OtherTypeView("INTJ", "Menghargai kedalaman wawasan mereka, meski kadang merasa pendekatan mereka kurang logis."),
            OtherTypeView("INTP", "Bingung dengan cara mereka mengambil kesimpulan 'mistis', tapi menghormati intuisi mereka."),
            OtherTypeView("ENTJ", "Melihatnya sebagai penasihat yang bijaksana, berguna untuk melihat 'sisi manusia' dari sebuah rencana."),
            OtherTypeView("ENTP", "Tertarik dengan kompleksitas pikiran mereka, suka mencoba 'membedah' logikanya."),
            OtherTypeView("INFP", "Merasa menemukan 'saudara', seseorang yang benar-benar mengerti kedalaman perasaan."),
            OtherTypeView("ENFJ", "Melihatnya sebagai versi diri yang lebih tenang dan reflektif, merasa ada ikatan kuat."),
            OtherTypeView("ENFP", "Merasa memiliki koneksi mendalam dengan mereka, menganggapnya sebagai jiwa yang bijaksana dan tua."),
            OtherTypeView("ISTJ", "Merasa mereka terlalu abstrak dan sulit ditebak."),
            OtherTypeView("ISFJ", "Mengagumi kebaikan dan empati mereka, sering menjadi pendengar setia."),
            OtherTypeView("ESTJ", "Menghargai integritas mereka, tetapi berpikir mereka terlalu idealis dan kurang praktis."),
            OtherTypeView("ESFJ", "Melihatnya sebagai teman curhat yang hebat, meski kadang terlalu banyak berpikir."),
            OtherTypeView("ISTP", "Merasa mereka terlalu rumit dan sulit dipahami."),
            OtherTypeView("ISFP", "Menghargai kelembutan dan sisi artistik mereka."),
            OtherTypeView("ESTP", "Bingung dengan sifat mereka yang tenang dan penuh teka-teki."),
            OtherTypeView("ESFP", "Menikmati energi positif mereka, tetapi merasa lelah jika terlalu lama bersama mereka.")
        ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "INFP" to PersonalityDetails(
        typeName = "INFP - Sang Mediator",
        description = "Ia seorang yang hatinya penuh ideal dan kasih sayang; ia hidup untuk prinsip dan makna. Dunia batinnya kaya akan imajinasi.",
        strengths = listOf("Sangat peduli & empati", "Kreatif & imajinatif", "Setia pada nilai", "Melihat potensi baik"),
        weaknesses = listOf("Terlalu idealis", "Sangat sensitif pada kritik", "Sulit bertindak", "Menghindari konfrontasi"),
        careerPaths = listOf("Penulis/Seniman", "Psikolog/Konselor", "Desainer", "Pekerja nirlaba"),
        relationships = "Dalam cinta, ia mencari keaslian—hubungan yang membiarkan kedua jiwa tumbuh. Ia memberi perhatian dengan cara yang lembut. Pasangan terbaik adalah yang menghormati kedalaman hatinya.",
        developmentTips = listOf("Ubah ideal menjadi langkah kecil.", "Lihat kritik sebagai peluang.", "Suarakan kebutuhanmu secara langsung."),
        detailImages = listOf(R.drawable.infpmain, R.drawable.infpdesc1, R.drawable.infpdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ENFJ" to PersonalityDetails(
        typeName = "ENFJ - Sang Protagonis",
        description = "Ia seorang yang memancarkan kehangatan dan dorongan untuk menggerakkan orang lain ke arah kebaikan. Kepeduliannya terasa nyata; ia sering menjadi katalis bagi perubahan positif.",
        strengths = listOf("Karismatik", "Sangat peka emosional", "Komunikatif", "Pemimpin suportif"),
        weaknesses = listOf("Mudah kelelahan karena orang lain", "Mencari validasi", "Menekan kebutuhan pribadi", "Sulit menerima konflik"),
        careerPaths = listOf("Guru/Mentor", "Manajer SDM", "Diplomat", "Konselor"),
        relationships = "Ia mencintai dengan keterlibatan penuh—mendengarkan, memotivasi, dan membantu pasangannya tumbuh. Ia memberi dukungan emosional yang kuat, namun kadang lupa merawat diri.",
        developmentTips = listOf("Sisihkan waktu untuk dirimu sendiri.", "Terima ketidaksempurnaan orang lain.", "Belajarlah meminta bantuan."),
        detailImages = listOf(R.drawable.enfjmain, R.drawable.enfjdesc1, R.drawable.enfjdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ENFP" to PersonalityDetails(
        typeName = "ENFP - Sang Juru Kampanye",
        description = "Ia seorang yang berdenyut penuh antusiasme dan rasa ingin tahu. Segala hal mengandung kemungkinan; ia menikmati menjelajahi hubungan, ide, dan pengalaman baru.",
        strengths = listOf("Energi tinggi", "Sangat kreatif", "Komunikatif & hangat", "Mudah beradaptasi"),
        weaknesses = listOf("Sulit fokus", "Rentan stres oleh rutinitas", "Menunda-nunda", "Terbawa emosi"),
        careerPaths = listOf("Kreator konten", "Aktivis", "Konsultan kreativitas", "Aktor/Presenter"),
        relationships = "Ia mencintai dengan antusiasme: penuh perhatian, spontan, dan ingin berbagi pengalaman. Ia mencari pasangan yang bisa menjadi sahabat sekaligus pendukung ide.",
        developmentTips = listOf("Belajar menyelesaikan sedikit demi sedikit.", "Praktikkan rutinitas kecil.", "Berikan batas pada energimu."),
        detailImages = listOf(R.drawable.enfpmain, R.drawable.enfpdesc1, R.drawable.enfpdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ISTJ" to PersonalityDetails(
        typeName = "ISTJ - Sang Ahli Logistik",
        description = "Ia seorang yang menepati janji dan menghargai struktur. Ketelitian dan rasa tanggung jawab adalah wataknya; dunia yang teratur memberinya rasa aman.",
        strengths = listOf("Sangat bertanggung jawab", "Teliti & detail", "Konsisten", "Etika kerja kuat"),
        weaknesses = listOf("Kurang fleksibel", "Kaku dalam berpikir", "Sulit ekspresi emosi", "Terlalu keras pada diri sendiri"),
        careerPaths = listOf("Akuntan/Auditor", "Manajer operasi", "Profesional logistik", "Analis data"),
        relationships = "Ia menunjukkan cinta lewat keandalan: hadir tepat waktu, menyelesaikan tanggung jawab, dan menyediakan dukungan praktis. Keteguhannya memberi rasa aman.",
        developmentTips = listOf("Beri ruang untuk fleksibilitas.", "Latih komunikasi kebutuhan emosional.", "Beri kesempatan ide baru."),
        detailImages = listOf(R.drawable.istjmain, R.drawable.istjdesc1, R.drawable.istjdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ISFJ" to PersonalityDetails(
        typeName = "ISFJ - Sang Pembela",
        description = "Ia seorang yang hangat, penuh perhatian, dan berdedikasi pada orang-orang yang ia sayangi. Ia melihat kebutuhan praktis serta emosional orang lain.",
        strengths = listOf("Sangat suportif & setia", "Peka kebutuhan orang lain", "Teliti & konsisten", "Sabar"),
        weaknesses = listOf("Mengorbankan diri", "Tidak nyaman dengan perubahan", "Menahan keluhan", "Sulit menetapkan batas"),
        careerPaths = listOf("Perawat/Bidan", "Guru sekolah dasar", "Pekerja sosial", "Desainer interior"),
        relationships = "Ia mencintai lewat tindakan: perhatian kecil, ingatan pada detail, dan usaha praktis untuk membuat pasangan merasa nyaman. Ia mencari stabilitas emosional.",
        developmentTips = listOf("Belajar mengatakan 'tidak'.", "Sisihkan waktu untuk kebutuhan pribadi.", "Terima pujian."),
        detailImages = listOf(R.drawable.isfjmain, R.drawable.isfjdesc1, R.drawable.isfjdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ESTJ" to PersonalityDetails(
        typeName = "ESTJ - Sang Eksekutif",
        description = "Ia seorang yang tangguh, terorganisir, dan mengutamakan praktik yang terbukti. Ia menemukan kenyamanan dalam struktur dan kepastian, dan sering menjadi penggerak yang memastikan segala sesuatunya berjalan sesuai rencana.",
        strengths = listOf("Kemampuan manajerial kuat", "Tegas & konsisten", "Sangat bertanggung jawab", "Pragmatis & fokus hasil"),
        weaknesses = listOf("Kurang fleksibel", "Cenderung menghakimi", "Sulit ekspresi emosi", "Mengabaikan perasaan individu"),
        careerPaths = listOf("Manajer operasional", "Hakim/Pengacara", "Manajer proyek", "Perwira militer"),
        relationships = "Ia menunjukkan cinta dengan stabilitas: komitmen yang jelas, perencanaan masa depan, dan tanggung jawab yang konsisten. Ia perlu belajar menampilkan kelembutan agar hubungan tak terasa semata kontrak.",
        developmentTips = listOf("Beri ruang pada emosi.", "Ekspresikan apresiasi dengan kata-kata.", "Rilekskan kendali pada hal kecil.", "Pelajari perspektif lain."),
        detailImages = listOf(R.drawable.estjmain, R.drawable.estjdesc1, R.drawable.estjdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ESFJ" to PersonalityDetails(
        typeName = "ESFJ - Sang Konsul",
        description = "Ia seorang yang ramah, peduli, dan terampil menjaga keharmonisan sosial. Ia mendapat kepuasan ketika membantu orang lain merasa diterima dan nyaman. Perhatiannya kepada detail hubungan membuatnya mudah menjadi pusat dukungan sosial.",
        strengths = listOf("Sangat sosial & perhatian", "Sangat setia", "Terorganisir dalam hubungan", "Peka norma sosial"),
        weaknesses = listOf("Mencari persetujuan eksternal", "Mengorbankan diri", "Mudah tersinggung oleh kritik", "Tidak nyaman dengan perubahan"),
        careerPaths = listOf("Event planner", "Perawat/Guru", "Manajer SDM", "Pekerja komunitas"),
        relationships = "Ia memberi cinta lewat perhatian dan pelayanan: merawat, mengingat tanggal penting, dan menyiapkan hal-hal kecil. Ia membutuhkan penghargaan yang jelas; bila diabaikan, ia mudah merasa terluka.",
        developmentTips = listOf("Belajar menetapkan batas.", "Terima bahwa penolakan itu sehat.", "Luangkan waktu untuk diri sendiri.", "Terima kritik sebagai alat berkembang."),
        detailImages = listOf(R.drawable.esfjmain, R.drawable.esfjdesc1, R.drawable.esfjdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ISTP" to PersonalityDetails(
        typeName = "ISTP - Sang Virtuoso",
        description = "Ia seorang yang tangkas dengan dunia konkret: memecahkan masalah praktis, mengutak-atik alat, dan beradaptasi cepat saat situasi berubah. Ia menikmati keterampilan tangan dan kebebasan untuk bereksperimen.",
        strengths = listOf("Keterampilan praktis", "Tenang dalam krisis", "Adaptif & spontan", "Berani mencoba"),
        weaknesses = listOf("Kurang sabar dengan struktur", "Sulit berkomitmen jangka panjang", "Menahan emosi", "Mengambil risiko berlebihan"),
        careerPaths = listOf("Mekanik/Pilot", "Insinyur lapangan", "Paramedis", "Programmer"),
        relationships = "Ia mendekati hubungan dengan praktikalitas: memberi dukungan lewat tindakan nyata. Ia menyukai pasangan yang memberinya kebebasan. Ketika merasa aman, ia sangat setia dan protektif.",
        developmentTips = listOf("Ekspresikan kebutuhan emosional.", "Pertimbangkan risiko jangka panjang.", "Latih konsistensi.", "Ngobrol tanpa agenda praktis."),
        detailImages = listOf(R.drawable.istpmain, R.drawable.istpdesc1, R.drawable.istpdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ISFP" to PersonalityDetails(
        typeName = "ISFP - Sang Petualang",
        description = "Ia seorang yang hidup dalam kepekaan terhadap pengalaman indrawi dan keindahan. Ia memilih menjalani hidup lewat perasaan dan ekspresi estetis, menghargai momen dan kebebasan untuk menjadi otentik.",
        strengths = listOf("Berjiwa artistik", "Hangat & nyaman", "Fleksibel", "Ekspresif"),
        weaknesses = listOf("Menghindari konflik", "Sulit berkomitmen jangka panjang", "Rentan stres oleh rutinitas", "Mudah terluka oleh kritik"),
        careerPaths = listOf("Seniman/Musisi", "Fotografer", "Koki", "Desainer fesyen"),
        relationships = "Ia mencintai lewat kehadiran yang lembut: sentuhan, perhatian pada detail kecil, dan berbagi pengalaman. Ia menghargai pasangan yang memberi ruang berekspresi. Ketika tertekan, ia cenderung mundur.",
        developmentTips = listOf("Sampaikan kebutuhan dengan kata-kata.", "Bangun rutinitas kecil.", "Hadapi konflik kecil.", "Sediakan waktu pribadi untuk emosi."),
        detailImages = listOf(R.drawable.isfpmain, R.drawable.isfpdesc1, R.drawable.isfpdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ESTP" to PersonalityDetails(
        typeName = "ESTP - Sang Pengusaha",
        description = "Ia seorang yang berani hidup di garis depan pengalaman—lari dari teori berlebih dan memilih aksi cepat. Ia gesit, pragmatis, dan menikmati tantangan langsung; dunia baginya adalah arena untuk bereaksi dan menangkap peluang.",
        strengths = listOf("Cepat bertindak", "Sangat adaptif", "Karismatik & persuasif", "Berani ambil keputusan"),
        weaknesses = listOf("Impulsif", "Kurang sabar dengan teori", "Mudah bosan", "Mengabaikan perasaan"),
        careerPaths = listOf("Pengusaha", "Sales", "Paramedis", "Atlet profesional"),
        relationships = "Ia memaknai hubungan lewat dinamika, aksi, dan pengalaman bersama. Ia menyukai pasangan yang spontan. Ia perlu belajar merencanakan dan konsisten pada komitmen yang tak selalu seru.",
        developmentTips = listOf("Nilai konsekuensi jangka panjang.", "Nikmati rutinitas kecil.", "Latih komunikasi perasaan.", "Buat rencana finansial."),
        detailImages = listOf(R.drawable.estpmain, R.drawable.estpdesc1, R.drawable.estpdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    ),
    "ESFP" to PersonalityDetails(
        typeName = "ESFP - Sang Penghibur",
        description = "Ia seorang yang hidup untuk berbagi kegembiraan. Kehadirannya membuat suasana lebih ringan dan hangat; ia pandai menemukan kesenangan di hal-hal sederhana dan membuat orang lain merasa diikutsertakan.",
        strengths = listOf("Membawa energi positif", "Mudah berbaur", "Peka estetika", "Cepat bertindak"),
        weaknesses = listOf("Kurang sabar dengan rencana", "Mudah terganggu", "Menghindari masalah serius", "Butuh validasi sosial"),
        careerPaths = listOf("Aktor/Entertainer", "Event planner", "Pemandu wisata", "Desainer visual"),
        relationships = "Ia memberi cinta lewat kehadiran riang: merencanakan kejutan, membawa keceriaan, dan membuat momen spesial. Ia butuh pasangan yang mencintai keaktifan dan tak kaku pada rencana.",
        developmentTips = listOf("Bangun komitmen kecil.", "Sediakan waktu refleksi.", "Hadapi percakapan serius.", "Pelihara keuangan."),
        detailImages = listOf(R.drawable.esfpmain, R.drawable.esfpdesc1, R.drawable.esfpdesc2),
        viewsFromOthers = listOf( /* Lengkapi di sini */ ),
        relationshipDetails = listOf( /* Lengkapi di sini */ )
    )
)

// 4. DATA UNTUK BINGO CARD
val personalityBingoMap: Map<String, List<BingoTrait>> = mapOf(
    "INTJ" to listOf(
        BingoTrait("SEMUANYA HARUS EFISIEN!", 2),
        BingoTrait("Skeptis alami"),
        BingoTrait("Terlalu banyak berpikir"),
        BingoTrait("Punya rencana untuk rencana cadangan"),
        BingoTrait("Tidak suka basa-basi"),
        BingoTrait("Menganalisis film setelah menonton"),
        BingoTrait("SARKASME ADALAH BAHASA KEDUA", 2),
        BingoTrait("Benci ketidakkompetenan"),
        BingoTrait("Lebih suka bekerja sendiri"),
        BingoTrait("Mencari logika di semua hal"),
        BingoTrait("Sulit mengungkapkan emosi"),
        BingoTrait("Pengetahuan adalah kekuatan"),
    ),
    "INTP" to listOf(
        BingoTrait("Raja Prokrastinasi"),
        BingoTrait("Pikiran bercabang ke 1000 arah"),
        BingoTrait("DEBAT UNTUK BERSENANG-SENANG", 2),
        BingoTrait("Lupa makan karena terlalu fokus"),
        BingoTrait("Tidak peduli dengan tren"),
        BingoTrait("Punya hobi yang sangat spesifik"),
        BingoTrait("Mengoreksi orang lain (dalam hati)"),
        BingoTrait("Kenapa harus sekarang jika bisa nanti?"),
        BingoTrait("MENYUKAI TEORI KONSPIRASI", 2),
        BingoTrait("Menghindari konflik emosional"),
        BingoTrait("Benci aturan yang tidak logis"),
    ),
    "ENTJ" to listOf(
        BingoTrait("TERLAHIR SEBAGAI PEMIMPIN", 2),
        BingoTrait("Sangat kompetitif"),
        BingoTrait("Tidak ada waktu untuk drama"),
        BingoTrait("Jadwal adalah segalanya"),
        BingoTrait("To-do list yang panjangnya 1 km"),
        BingoTrait("Selalu punya opini"),
        BingoTrait("Efisien atau mati"),
        BingoTrait("MENGORGANISIR HIDUP ORANG LAIN", 2),
        BingoTrait("Tidak takut konflik"),
        BingoTrait("Mengambil alih secara alami"),
        BingoTrait("Sangat blak-blakan"),
    ),
    "ENTP" to listOf(
        BingoTrait("Devil's Advocate Profesional"),
        BingoTrait("Memulai 10 proyek, menyelesaikan 1"),
        BingoTrait("SUKA MEMULAI DEBAT", 2),
        BingoTrait("Alergi terhadap jadwal ketat"),
        BingoTrait("Penuh dengan ide random"),
        BingoTrait("Bosan adalah musuh utama"),
        BingoTrait("Karismatik & lucu"),
        BingoTrait("Mengapa harus ikut aturan?"),
        BingoTrait("MASTER PROKRASTINASI KREATIF", 2),
        BingoTrait("Melihat semua sisi argumen"),
        BingoTrait("Energinya tak terbatas (kadang-kadang)"),
    ),
    "INFJ" to listOf(
        BingoTrait("CINTA DAMAI", 2),
        BingoTrait("Suka berbuat kebaikan <3"),
        BingoTrait("Sensitif dengan perasaan orang lain"),
        BingoTrait("IDEALIS"),
        BingoTrait("Menyembunyikan emosi yang sedang dirasakan"),
        BingoTrait("Menyukai keteraturan"),
        BingoTrait("Sulit percaya dengan orang baru (Trust Issue)"),
        BingoTrait("Tertarik dengan teori-teori konspirasi"),
        BingoTrait("DEEP TALK ITU MENARIK!", 2),
        BingoTrait("Perfeksionis"),
        BingoTrait("Sering merasa diperhatikan oleh orang sekitar"),
        BingoTrait("KREATIF!"),
        BingoTrait("Seorang pendengar yang baik"),
        BingoTrait("Senang mempelajari kepribadian manusia"),
        BingoTrait("BERPRINSIP KUAT"),
        BingoTrait("Senang menggambar atau menulis"),
    ),
    "INFP" to listOf(
        BingoTrait("Melamun adalah hobi"),
        BingoTrait("Punya dunia fantasi sendiri"),
        BingoTrait("TERLALU IDEALIS", 2),
        BingoTrait("Menyerap emosi orang lain seperti spons"),
        BingoTrait("Sangat setia pada nilai"),
        BingoTrait("Menulis puisi atau jurnal"),
        BingoTrait("Suka musik indie"),
        BingoTrait("Sulit membuat keputusan"),
        BingoTrait("CINTA ESTETIKA & SENJA", 2),
        BingoTrait("Menghindari konflik sebisa mungkin"),
        BingoTrait("Merasa berbeda dari orang lain"),
    ),
    "ENFJ" to listOf(
        BingoTrait("PEMIMPIN YANG MENGINSPIRASI", 2),
        BingoTrait("Ingin membantu semua orang"),
        BingoTrait("Sangat peka terhadap kritik"),
        BingoTrait("Terlalu memikirkan pendapat orang"),
        BingoTrait("Organizer acara dadakan"),
        BingoTrait("Pandai membaca suasana"),
        BingoTrait("Mengorbankan diri untuk orang lain"),
        BingoTrait("Suka memberi nasihat"),
        BingoTrait("SI SOCIAL BUTTERFLY", 2),
        BingoTrait("Selalu positif (atau pura-pura)"),
        BingoTrait("Punya banyak teman"),
    ),
    "ENFP" to listOf(
        BingoTrait("Antusiasme 24/7"),
        BingoTrait("Punya 100 ide per menit"),
        BingoTrait("Teman curhat semua orang"),
        BingoTrait("Benci rutinitas"),
        BingoTrait("Melihat potensi di semua hal"),
        BingoTrait("Spontan & fleksibel"),
        BingoTrait("Suka mencoba hal baru"),
        BingoTrait("SULIT FOKUS PADA SATU HAL", 2),
        BingoTrait("Selalu menemukan alasan untuk 'celebrate'"),
        BingoTrait("Energi tak ada habisnya"),
        BingoTrait("Sangat ekspresif"),
    ),
    "ISTJ" to listOf(
        BingoTrait("BERPEGANG PADA ATURAN", 2),
        BingoTrait("Sangat terorganisir"),
        BingoTrait("Menghargai tradisi"),
        BingoTrait("Tidak suka kejutan"),
        BingoTrait("Praktis dan realistis"),
        BingoTrait("Bertanggung jawab penuh"),
        BingoTrait("Bekerja keras dalam diam"),
        BingoTrait("Sangat detail"),
        BingoTrait("Butuh persiapan untuk segalanya"),
        BingoTrait("SETIA DAN DAPAT DIANDALKAN", 2),
        BingoTrait("Sulit menerima perubahan"),
    ),
    "ISFJ" to listOf(
        BingoTrait("SANGAT PEDULI & PERHATIAN", 2),
        BingoTrait("Mengingat detail kecil tentang orang"),
        BingoTrait("Tidak suka menjadi pusat perhatian"),
        BingoTrait("Selalu siap membantu"),
        BingoTrait("Sulit berkata 'tidak'"),
        BingoTrait("Setia kawan"),
        BingoTrait("Menjaga harmoni"),
        BingoTrait("Praktis & rendah hati"),
        BingoTrait("Butuh waktu sendiri untuk 'recharge'"),
        BingoTrait("PELINDUNG ORANG TERSAYANG", 2),
        BingoTrait("Terlalu memikirkan masa lalu"),
    ),
    "ESTJ" to listOf(
        BingoTrait("EFISIENSI DI ATAS SEGALANYA", 2),
        BingoTrait("Pemimpin alami"),
        BingoTrait("Sangat terstruktur"),
        BingoTrait("Tidak suka omong kosong"),
        BingoTrait("Tegas dan blak-blakan"),
        BingoTrait("Menghormati hierarki"),
        BingoTrait("Pekerja keras"),
        BingoTrait("Menyelesaikan pekerjaan"),
        BingoTrait("Suka mengatur orang"),
        BingoTrait("LOGIS DAN OBJEKTIF", 2),
        BingoTrait("Sangat bertanggung jawab"),
    ),
    "ESFJ" to listOf(
        BingoTrait("POPULER & RAMAH", 2),
        BingoTrait("Ingin semua orang bahagia"),
        BingoTrait("Tuan rumah yang hebat"),
        BingoTrait("Sangat peduli pada komunitas"),
        BingoTrait("Menghargai tradisi sosial"),
        BingoTrait("Suka menolong"),
        BingoTrait("Praktis dan terorganisir"),
        BingoTrait("Butuh validasi dari orang lain"),
        BingoTrait("Pandai menjaga hubungan"),
        BingoTrait("SOCIAL BUTTERFLY SEJATI", 2),
        BingoTrait("Sangat setia"),
    ),
    "ISTP" to listOf(
        BingoTrait("TENANG TAPI SIAP BERTINDAK", 2),
        BingoTrait("Suka membongkar barang"),
        BingoTrait("Pemecah masalah praktis"),
        BingoTrait("Tidak suka teori berbelit-belit"),
        BingoTrait("Sangat mandiri"),
        BingoTrait("Suka tantangan fisik"),
        BingoTrait("Fleksibel dan spontan"),
        BingoTrait("Logis dan analitis"),
        BingoTrait("Menguasai alat atau keahlian"),
        BingoTrait("OBSERVATOR YANG TAJAM", 2),
        BingoTrait("Bosan dengan rutinitas"),
    ),
    "ISFP" to listOf(
        BingoTrait("SENIMAN SEJATI", 2),
        BingoTrait("Hidup di saat ini"),
        BingoTrait("Sangat sadar akan lingkungan sekitar"),
        BingoTrait("Ekspresif secara non-verbal"),
        BingoTrait("Tidak suka dikritik"),
        BingoTrait("Punya selera estetika yang kuat"),
        BingoTrait("Setia pada lingkaran kecil"),
        BingoTrait("Suka petualangan spontan"),
        BingoTrait("Menghargai kebebasan pribadi"),
        BingoTrait("FLEKSIBEL DAN MENAWAN", 2),
        BingoTrait("Cenderung menghindari konflik"),
    ),
    "ESTP" to listOf(
        BingoTrait("ENERGI TINGGI & BERANI", 2),
        BingoTrait("Suka menjadi pusat perhatian"),
        BingoTrait("Cepat bosan"),
        BingoTrait("Pemecah masalah yang cepat"),
        BingoTrait("Sangat persuasif"),
        BingoTrait("Hidup untuk saat ini"),
        BingoTrait("Suka mengambil risiko"),
        BingoTrait("Pandai beradaptasi"),
        BingoTrait("Sangat observatif"),
        BingoTrait("LANGSUNG KE INTINYA", 2),
        BingoTrait("Ahli dalam negosiasi"),
    ),
    "ESFP" to listOf(
        BingoTrait("SANG PENGHIBUR SEJATI", 2),
        BingoTrait("Spontan dan menyenangkan"),
        BingoTrait("Suka menjadi sorotan"),
        BingoTrait("Praktis dan membumi"),
        BingoTrait("Sangat murah hati"),
        BingoTrait("Pandai membuat orang lain tertawa"),
        BingoTrait("Tidak suka sendirian"),
        BingoTrait("Optimis dan bersemangat"),
        BingoTrait("Estetika adalah segalanya"),
        BingoTrait("HIDUP UNTUK PESTA", 2),
        BingoTrait("Sangat sadar sosial"),
    ),
)


// --- FUNGSI UTILITAS ---
/**
 * Fungsi untuk mendapatkan tema warna berdasarkan nama grup.
 */
fun getThemeForMbtiGroup(groupTitle: String): GroupTheme {
    return when (groupTitle) {
        "Para Analis" -> analystTheme
        "Para Diplomat" -> diplomatTheme
        "Para Penjaga" -> sentinelTheme
        "Para Perajin" -> explorerTheme
        else -> diplomatTheme // Default theme
    }
}
