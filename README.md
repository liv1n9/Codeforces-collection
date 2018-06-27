<h1>Introduce</h1>
<p><strong>Codeforces-collection</strong> is a Java tool. It can help get all accepted submissions from Codeforces which are not Gym's submissions.</p>
<p>Codeforces-collection uses <strong>SDK 1.8.0_162</strong>. You can simply run the application by executing <strong>codeforces-collection/out/artifacts/codeforces_collection_jar</strong>.</p>
<p>Codeforces-collection can convert submissions of languages: C++, C, C#, Java, Python, JavaScript and Pascal to files with correspond extensions such as ".cpp", ".java", ".py", ... The others languages will have empty extensions, for example: "."</p>
<p>Codeforces-collection works on Windows 10. It hasn't been tested on others operating systems.</>
<h1>Instruction</h1>
<p>You need to enter a Codeforces's handle, set directory where you store the submissions, and choose mode.</p>
<p>There are 3 modes: <strong>All</strong>, <strong>Time</strong> and <strong>Memory</strong>.</p>
<ul>
<li><strong>All</strong>: You will be able to get all public accepted submissions of the account you choosed, which are every submissions except Gym's. Each of them which solved problem <em>&lt;problem_index&gt;</em> of contest with index <em>&lt;contest_id&gt;</em> will be stored in a folder named <em>&lt;contest_id&gt;&lt;problem_index&gt;</em>, for example: 221B. If there are more than one accepted solutions of one problem, they will be stored in multiple folders with same prefix <em>&lt;contest_id&gt;&lt;problem_index&gt;</em>, and have different suffixes numbered from 1 &nbsp;<em>&lt;_number&gt;</em></li>, for example: 265A_1, 265A_2, 265A_3.
  <li><strong>Time</strong>: Submissions will be stored in a similar way to <strong>"All"</strong> mode. The different is the only submission with the fastest running time will be chosen per problem.
  <li><strong>Memory</strong>:  Similar to <strong>"Time"</strong> mode, but the submission with the smallest memory will be chosen instead.
</ul>
