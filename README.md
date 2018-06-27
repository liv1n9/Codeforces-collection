# Codeforces-collection
<h1>Introduce</h1>
<p>Codeforces-collection is a Java tool. It can help get all accepted sources from Codeforces which are not Gym's submissions.</p>
<p>Codeforces-collection using <strong>SDK 1.8.0_162</strong>. You can simply run the application by executing <strong>codeforces-collection/out/artifacts/codeforces_collection_jar</strong>.</p>
<h1>Help</h1>
<p>You have to enter your Codeforces's handle, set directory where you store the sources, and choose mode.</p>
<p>The mode includes 3 modes: <strong>All</strong>, <strong>Time</strong> and <strong>Memory</strong>.</p>
<ul>
<li><strong>All</strong>: You will get all your accepted sources. Every source will be stored in a folder named <em>&lt;contest_id&gt;&lt;problem_index&gt;</em>, for example: 221B. If you made more than one accepted solutions of one problem, those sources will be stored in multiple folders with same prefix <em>&lt;contest_id&gt;&lt;problem_index&gt;</em>, and have different suffixes&nbsp;<em>&lt;_number&gt;</em></li>
, for example: 221B_1, 221B_2, 221B_3, ...
  <li><strong>Time</strong>: Sources will be stored in a similar way to mode <strong>All</strong>. The different is the only source with the fastest running time will be chosen per problem.
  <li><strong>Memory</strong>:  Sources will be stored in a similar way to mode <strong>Time</strong>, but the source with the smallest memory will be chosen instead.
</ul>
