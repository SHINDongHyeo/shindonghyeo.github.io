// 한국어 기준 분당 약 500자로 계산
export function readingMinutes(body = ''): number {
	return Math.max(1, Math.round(body.replace(/\s/g, '').length / 500));
}

export function formatDate(date: Date): string {
	return date.toLocaleDateString('ko-KR', {
		year: 'numeric',
		month: 'long',
		day: 'numeric',
	});
}
